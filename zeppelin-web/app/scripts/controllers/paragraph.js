/* global $:false, jQuery:false, ace:false, confirm:false, d3:false, nv:false*/
/*jshint loopfunc: true, unused:false */
/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
'use strict';

/**
 * @ngdoc function
 * @name zeppelinWebApp.controller:ParagraphCtrl
 * @description
 * # ParagraphCtrl
 * Controller of the paragraph, manage everything related to the paragraph
 *
 * @author anthonycorbacho
 */
angular.module('zeppelinWebApp')
        .controller('ParagraphCtrl', function($scope, $rootScope, $route, $window, $element, $routeParams, $location, $timeout, $compile, UtilService) {

  $scope.paragraph = null;
  $scope.editor = null;
  var editorMode = {scala: 'ace/mode/scala', sql: 'ace/mode/sql', markdown: 'ace/mode/markdown', chartsql: 'ace/mode/chartsql'};
  var rowIndexKeyColumnName = 'rowNumIndexColumn'; // key 컬럼이 미지정 된경우, key 컬럼으로 사용하기 위함
  var dependencyFilter = undefined;
  var filteredParagraphResult = undefined;

  // Controller init
  $scope.init = function(newParagraph) {
    $scope.paragraph = newParagraph;
    $scope.chart = {};
    $scope.colWidthOption = [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 ];
    $scope.showTitleEditor = false;
    $scope.paragraphFocused = false;

    if (!$scope.paragraph.config) {
      $scope.paragraph.config = {};
    }

    initializeDefault();

    if (!$scope.lastData) {
      $scope.lastData = {};
    }

    if ($scope.getResultType() === 'TABLE') {
      $scope.lastData.settings = angular.copy($scope.paragraph.settings);
      $scope.lastData.config = angular.copy($scope.paragraph.config);
      $scope.loadTableData($scope.paragraph.result);
      $scope.setGraphMode($scope.getGraphMode(), false, false);
    } else if($scope.getResultType() === 'CHART') {
      $scope.lastData.settings = angular.copy($scope.paragraph.settings);
      $scope.lastData.config = angular.copy($scope.paragraph.config);
      $scope.loadTableData($scope.paragraph.result);      
      //조회데이타에서 차트의 종류를 결정한다.
      $scope.paragraph.config.graph.mode = $scope.paragraph.result.mode;
      if($scope.getGraphMode() === 'mapchart') {
        var type = $scope.getGraphMode();
        var height = $scope.paragraph.config.graph.height;
        $('#p'+$scope.paragraph.id+'_'+type+' .angular-google-map-container').css('height', height);
      }
      selectDefaultColsForGraphOptionByIndex();
      $scope.setGraphMode($scope.getGraphMode(), false, true);
    }else if ($scope.getResultType() === 'HTML') {
      $scope.renderHtml();
    } else if ($scope.getResultType() === 'ANGULAR') {
      $scope.renderAngular();
    }
  };

  $scope.renderHtml = function() {
    var retryRenderer = function() {
      if ($('#p'+$scope.paragraph.id+'_html').length) {
        try {
          $('#p'+$scope.paragraph.id+'_html').html($scope.paragraph.result.msg);

          $('#p'+$scope.paragraph.id+'_html').find('pre code').each(function(i, e) { hljs.highlightBlock(e) });
        } catch(err) {
          console.log('HTML rendering error %o', err);
        }
      } else {
        $timeout(retryRenderer,10);
      }
    };
    $timeout(retryRenderer);

  };

  $scope.renderAngular = function() {
    var retryRenderer = function() {
      if (angular.element('#p'+$scope.paragraph.id+'_angular').length) {
        try {
          angular.element('#p'+$scope.paragraph.id+'_angular').html($scope.paragraph.result.msg);

          $compile(angular.element('#p'+$scope.paragraph.id+'_angular').contents())($rootScope.compiledScope);
        } catch(err) {
          console.log('ANGULAR rendering error %o', err);
        }
      } else {
        $timeout(retryRenderer,10);
      }
    };
    $timeout(retryRenderer);

  };


  var initializeDefault = function() {
    var config = $scope.paragraph.config;

    if (!config.colWidth) {
      config.colWidth = 12;
    }

    if (!config.graph) {
      config.graph = {};
    }

    if (!config.graph.mode) {
      config.graph.mode = 'table';
    }

    if (!config.graph.height) {
      config.graph.height = 300;
    }

    if (!config.graph.optionOpen) {
      config.graph.optionOpen = false;
    }

    if (!config.graph.keys) {
      config.graph.keys = [];
    }

    if (!config.graph.values) {
      config.graph.values = [];
    }

    if (!config.graph.groups) {
      config.graph.groups = [];
    }

    if (!config.graph.scatter) {
      config.graph.scatter = {};
    }

    if (!config.label) {
      config.label = {};
    }

    if (!config.dependencyParagraph && ($scope.paragraph.id === '20150728-065019_1199988223' || $scope.paragraph.id === '20150728-074555_65315767')) {
      config.dependencyParagraph = [{id:"20150728-061703_1809065084"}];
      //TODO
    }

    //if (!config.dataFilter) {
      config.dataFilter = undefined;
    //}
  };

  $scope.getIframeDimensions = function () {
    if ($scope.asIframe) {
      var paragraphid = '#' + $routeParams.paragraphId + '_container';
      var height = $(paragraphid).height();
      return height;
    }
    return 0;
  };

  $scope.$watch($scope.getIframeDimensions, function (newValue, oldValue) {
    if ($scope.asIframe && newValue) {
      var message = {};
      message.height = newValue;
      message.url = $location.$$absUrl;
      $window.parent.postMessage(angular.toJson(message), '*');
    }
  });

  // TODO: this may have impact on performance when there are many paragraphs in a note.
  $scope.$on('updateParagraph', function(event, data) {
    
    if (data.paragraph.id === $scope.paragraph.id &&
         (
             data.paragraph.dateCreated !== $scope.paragraph.dateCreated ||
             data.paragraph.dateFinished !== $scope.paragraph.dateFinished ||
             data.paragraph.dateStarted !== $scope.paragraph.dateStarted ||
             data.paragraph.status !== $scope.paragraph.status ||
             data.paragraph.jobName !== $scope.paragraph.jobName ||
             data.paragraph.title !== $scope.paragraph.title ||
             data.paragraph.errorMessage !== $scope.paragraph.errorMessage ||
             !angular.equals(data.paragraph.settings, $scope.lastData.settings) ||
             !angular.equals(data.paragraph.config, $scope.lastData.config)
         )
       ) {
      // store original data for comparison
      $scope.lastData.settings = angular.copy(data.paragraph.settings);
      $scope.lastData.config = angular.copy(data.paragraph.config);

      var oldType = $scope.getResultType();
      var newType = $scope.getResultType(data.paragraph);
      var oldGraphMode = $scope.getGraphMode();
      var newGraphMode = $scope.getGraphMode(data.paragraph);
      var resultRefreshed = (data.paragraph.dateFinished !== $scope.paragraph.dateFinished);
      
      var isChangedOptionOpen = false;
      if($scope.paragraph.config.graph.optionOpen !== undefined 
        && data.paragraph.config.graph !== undefined 
        && data.paragraph.config.graph.optionOpen !== undefined) {
        isChangedOptionOpen = ($scope.paragraph.config.graph.optionOpen !== data.paragraph.config.graph.optionOpen);
      }
      if(!isChangedOptionOpen && $scope.paragraph.config.editorHide !== undefined && data.paragraph.config.editorHide !== undefined) {
        isChangedOptionOpen = ($scope.paragraph.config.editorHide !== data.paragraph.config.editorHide);
      }
      if(!isChangedOptionOpen && $scope.paragraph.config.tableHide !== undefined && data.paragraph.config.tableHide !== undefined) {
        isChangedOptionOpen = ($scope.paragraph.config.tableHide !== data.paragraph.config.tableHide);
      }
      console.info('isChangedOptionOpen', isChangedOptionOpen);
      
      //console.log("updateParagraph oldData %o, newData %o. type %o -> %o, mode %o -> %o", $scope.paragraph, data, oldType, newType, oldGraphMode, newGraphMode);

      if ($scope.paragraph.text !== data.paragraph.text) {
        if ($scope.dirtyText) {         // check if editor has local update
          if ($scope.dirtyText === data.paragraph.text ) {  // when local update is the same from remote, clear local update
            $scope.paragraph.text = data.paragraph.text;
            $scope.dirtyText = undefined;
          } else { // if there're local update, keep it.
            $scope.paragraph.text = $scope.dirtyText;
          }
        } else {
          $scope.paragraph.text = data.paragraph.text;
        }
      }

      /** push the rest */
      $scope.paragraph.aborted = data.paragraph.aborted;
      $scope.paragraph.dateCreated = data.paragraph.dateCreated;
      $scope.paragraph.dateFinished = data.paragraph.dateFinished;
      $scope.paragraph.dateStarted = data.paragraph.dateStarted;
      $scope.paragraph.errorMessage = data.paragraph.errorMessage;
      $scope.paragraph.jobName = data.paragraph.jobName;
      $scope.paragraph.title = data.paragraph.title;
      $scope.paragraph.status = data.paragraph.status;
      $scope.paragraph.result = data.paragraph.result;
      $scope.paragraph.settings = data.paragraph.settings;

      if (!$scope.asIframe) {
        $scope.paragraph.config = data.paragraph.config;
        initializeDefault();
      } else {
        data.paragraph.config.editorHide = true;
        data.paragraph.config.tableHide = false;
        $scope.paragraph.config = data.paragraph.config;
      }
              
      if (newType === 'TABLE') {
        $scope.loadTableData($scope.paragraph.result);
        if (oldType !== 'TABLE' || resultRefreshed) {
          clearUnknownColsFromGraphOption();
          selectDefaultColsForGraphOption();
        }
        /** User changed the chart type? */
        if (oldGraphMode !== newGraphMode) {
          $scope.setGraphMode(newGraphMode, false, false);
        } else {
          $scope.setGraphMode(newGraphMode, false, true);
        }
      } else if (newType === 'CHART') {
        $scope.loadTableData($scope.paragraph.result);
        data.paragraph.config.graph.mode = $scope.paragraph.result.mode;
        $scope.paragraph.config.graph.mode = $scope.paragraph.result.mode;
        newGraphMode = $scope.getGraphMode(data.paragraph);

        if (oldType !== 'CHART' || resultRefreshed) {
          clearUnknownColsFromGraphOption();
          selectDefaultColsForGraphOptionByIndex();
        }
        if (oldGraphMode !== newGraphMode) {
          $scope.paragraph.config.label = {};
        }
        /** User changed the chart type? */
        //if (isChangedOptionOpen || oldGraphMode !== newGraphMode) {
        if (isChangedOptionOpen) {
          $scope.setGraphMode(newGraphMode, false, false);
        } else {
          $scope.setGraphMode(newGraphMode, false, true);
        }
      } else if (newType === 'HTML') {
        $scope.renderHtml();
      } else if (newType === 'ANGULAR') {
        $scope.renderAngular();
      }
    }
  });

  $scope.isRunning = function() {
    if ($scope.paragraph.status === 'RUNNING' || $scope.paragraph.status === 'PENDING') {
      return true;
    } else {
      return false;
    }
  };

  $scope.cancelParagraph = function() {
    console.log('Cancel %o', $scope.paragraph.id);
    var data = {op: 'CANCEL_PARAGRAPH', data: {id: $scope.paragraph.id }};
    $rootScope.$emit('sendNewEvent', data);
  };


  $scope.runParagraph = function(data) {
    var parapgraphData = {op: 'RUN_PARAGRAPH',
                          data: {
                              id: $scope.paragraph.id,
                              title: $scope.paragraph.title,
                              paragraph: data,
                              config: $scope.paragraph.config,
                              params: $scope.paragraph.settings.params
                          }
                         };
    $rootScope.$emit('sendNewEvent', parapgraphData);
  };

  $scope.moveUp = function() {
    $scope.$emit('moveParagraphUp', $scope.paragraph.id);
  };

  $scope.moveDown = function() {
    $scope.$emit('moveParagraphDown', $scope.paragraph.id);
  };

  $scope.insertNew = function() {
    $scope.$emit('insertParagraph', $scope.paragraph.id);
  };

  $scope.removeParagraph = function() {
    var result = confirm('Do you want to delete this paragraph?');
    if (result) {
      console.log('Remove paragraph');
      var paragraphData = {op: 'PARAGRAPH_REMOVE', data: {id: $scope.paragraph.id}};
      $rootScope.$emit('sendNewEvent', paragraphData);
    }
  };

  $scope.toggleEditor = function() {
    if ($scope.paragraph.config.editorHide) {
      $scope.openEditor();
    } else {
      $scope.closeEditor();
    }
  };

  $scope.closeEditor = function() {
    console.log('close the note');

    var newParams = angular.copy($scope.paragraph.settings.params);
    var newConfig = angular.copy($scope.paragraph.config);
    newConfig.editorHide = true;

    commitParagraph($scope.paragraph.title, $scope.paragraph.text, newConfig, newParams);
  };

  $scope.openEditor = function() {
    console.log('open the note');

    var newParams = angular.copy($scope.paragraph.settings.params);
    var newConfig = angular.copy($scope.paragraph.config);
    newConfig.editorHide = false;

    commitParagraph($scope.paragraph.title, $scope.paragraph.text, newConfig, newParams);
  };

  $scope.closeTable = function() {
    console.log('close the output');

    var newParams = angular.copy($scope.paragraph.settings.params);
    var newConfig = angular.copy($scope.paragraph.config);
    newConfig.tableHide = true;

    commitParagraph($scope.paragraph.title, $scope.paragraph.text, newConfig, newParams);
  };

  $scope.openTable = function() {
    console.log('open the output');

    var newParams = angular.copy($scope.paragraph.settings.params);
    var newConfig = angular.copy($scope.paragraph.config);
    newConfig.tableHide = false;

    commitParagraph($scope.paragraph.title, $scope.paragraph.text, newConfig, newParams);
  };

  $scope.showTitle = function() {
    var newParams = angular.copy($scope.paragraph.settings.params);
    var newConfig = angular.copy($scope.paragraph.config);
    newConfig.title = true;

    commitParagraph($scope.paragraph.title, $scope.paragraph.text, newConfig, newParams);
  };

  $scope.hideTitle = function() {
    var newParams = angular.copy($scope.paragraph.settings.params);
    var newConfig = angular.copy($scope.paragraph.config);
    newConfig.title = false;

    commitParagraph($scope.paragraph.title, $scope.paragraph.text, newConfig, newParams);
  };

  $scope.setTitle = function() {
    var newParams = angular.copy($scope.paragraph.settings.params);
    var newConfig = angular.copy($scope.paragraph.config);
    commitParagraph($scope.paragraph.title, $scope.paragraph.text, newConfig, newParams);
  };

  $scope.columnWidthClass = function(n) {
    if ($scope.asIframe) {
      return 'col-md-12';
    } else {
      return 'col-md-' + n;
    }
  };

  $scope.alignMultichartSettingGridCol = function() {
    var sm = 6; // sm 에서는 paragraph 12 고정으로 세팅되어서 sm 은 가변적으로 변경하지 않음
    var md = 6;
    var lg = 5;
    switch($scope.paragraph.config.colWidth) {
      case 1:
        sm = 6; md = 12; lg = 12; break;
      case 2:
        sm = 6; md = 12; lg = 12; break;
      case 3:
        sm = 6; md = 12; lg = 12; break;
      case 4:
        sm = 6; md = 12; lg = 12; break;
      case 5:
        sm = 6; md = 12; lg = 12; break;
      case 6:
        sm = 6; md = 12; lg = 12; break;
      case 7:
        sm = 6; md = 9; lg = 8; break;
      case 8:
        sm = 6; md = 8; lg = 7; break;
      case 9:
        sm = 6; md = 7; lg = 6; break;
      case 10:
        sm = 6; md = 6; lg = 5; break;
      case 11:
        sm = 6; md = 6; lg = 4; break;
      case 12:
        sm = 6; md = 6; lg = 4; break;
    }
    //console.info($scope.paragraph.config.colWidth + ':    col-sm-' + sm + ' col-md-' + md + ' col-lg-' + lg);
    return 'col-sm-' + sm + ' col-md-' + md + ' col-lg-' + lg;
  };  
  
  $scope.changeColWidth = function() {

    var newParams = angular.copy($scope.paragraph.settings.params);
    var newConfig = angular.copy($scope.paragraph.config);

    commitParagraph($scope.paragraph.title, $scope.paragraph.text, newConfig, newParams);
  };

  $scope.toggleGraphOption = function() {
    var newConfig = angular.copy($scope.paragraph.config);
    if (newConfig.graph.optionOpen) {
      newConfig.graph.optionOpen = false;
    } else {
      newConfig.graph.optionOpen = true;
    }
    var newParams = angular.copy($scope.paragraph.settings.params);

    commitParagraph($scope.paragraph.title, $scope.paragraph.text, newConfig, newParams);
  };

  $scope.toggleOutput = function() {
    var newConfig = angular.copy($scope.paragraph.config);
    newConfig.tableHide = !newConfig.tableHide;
    var newParams = angular.copy($scope.paragraph.settings.params);

    commitParagraph($scope.paragraph.title, $scope.paragraph.text, newConfig, newParams);
  };
  
  $scope.changeChartOption = function() {
    var newConfig = angular.copy($scope.paragraph.config);
    var newParams = angular.copy($scope.paragraph.settings.params);

    commitParagraph($scope.paragraph.title, $scope.paragraph.text, newConfig, newParams);
  };


  $scope.loadForm = function(formulaire, params) {
    var value = formulaire.defaultValue;
    if (params[formulaire.name]) {
      value = params[formulaire.name];
    }

    if (value === '') {
      value = formulaire.options[0].value;
    }

    $scope.paragraph.settings.params[formulaire.name] = value;
  };

  $scope.aceChanged = function() {
    $scope.dirtyText = $scope.editor.getSession().getValue();
  };

  $scope.aceLoaded = function(_editor) {
    var langTools = ace.require('ace/ext/language_tools');
    var Range = ace.require('ace/range').Range;

    $scope.editor = _editor;
    if (_editor.container.id !== '{{paragraph.id}}_editor') {
      $scope.editor.renderer.setShowGutter(false);
      $scope.editor.setHighlightActiveLine(false);
      $scope.editor.setTheme('ace/theme/github');
      $scope.editor.focus();
      var hight = $scope.editor.getSession().getScreenLength() * $scope.editor.renderer.lineHeight + $scope.editor.renderer.scrollBar.getWidth();
      setEditorHeight(_editor.container.id, hight);

      $scope.editor.getSession().setUseWrapMode(true);
      if (navigator.appVersion.indexOf('Mac') !== -1 ) {
        $scope.editor.setKeyboardHandler('ace/keyboard/emacs');
      } else if (navigator.appVersion.indexOf('Win') !== -1 ||
                 navigator.appVersion.indexOf('X11') !== -1 ||
                 navigator.appVersion.indexOf('Linux') !== -1) {
        // not applying emacs key binding while the binding override Ctrl-v. default behavior of paste text on windows.
      }

      $scope.editor.setOptions({
          enableBasicAutocompletion: true,
          enableSnippets: false,
          enableLiveAutocompletion:false
      });
      var remoteCompleter = {
          getCompletions : function(editor, session, pos, prefix, callback) {
              if (!$scope.editor.isFocused() ){ return;}

              var buf = session.getTextRange(new Range(0, 0, pos.row, pos.column));
              $rootScope.$emit('sendNewEvent', {
                  op : 'COMPLETION',
                  data : {
                      id : $scope.paragraph.id,
                      buf : buf,
                      cursor : buf.length
                  }
              });

              $scope.$on('completionList', function(event, data) {
                  if (data.completions) {
                      var completions = [];
                      for (var c in data.completions) {
                          var v = data.completions[c];
                          completions.push({
                              name:v,
                              value:v,
                              score:300
                          });
                      }
                      callback(null, completions);
                  }
              });
          }
      };
      langTools.addCompleter(remoteCompleter);


      $scope.handleFocus = function(value) {
        $scope.paragraphFocused = value;
        // Protect against error in case digest is already running
        $timeout(function() {
          // Apply changes since they come from 3rd party library
          $scope.$digest();
        });
      };

      $scope.editor.on('focus', function() {
        $scope.handleFocus(true);
      });

      $scope.editor.on('blur', function() {
        $scope.handleFocus(false);
      });


      $scope.editor.getSession().on('change', function(e, editSession) {
        hight = editSession.getScreenLength() * $scope.editor.renderer.lineHeight + $scope.editor.renderer.scrollBar.getWidth();
        setEditorHeight(_editor.container.id, hight);
        $scope.editor.resize();
      });

      var code = $scope.editor.getSession().getValue();
      if ( String(code).startsWith('%sql')) {
        $scope.editor.getSession().setMode(editorMode.sql);
      } else if ( String(code).startsWith('%md')) {
        $scope.editor.getSession().setMode(editorMode.markdown);
      } else if ( String(code).startsWith('%elasticsearch')) {
        var ChartsqlMode = ace.require(editorMode.chartsql).Mode;
        $scope.editor.getSession().setMode(new ChartsqlMode());
      } else {
        $scope.editor.getSession().setMode(editorMode.scala);
      }

      $scope.editor.commands.addCommand({
        name: 'run',
        bindKey: {win: 'Shift-Enter', mac: 'Shift-Enter'},
        exec: function(editor) {
          var editorValue = editor.getValue();
          if (editorValue) {
            $scope.runParagraph(editorValue);
          }
        },
        readOnly: false
      });

      // autocomplete on '.'
      /*
      $scope.editor.commands.on("afterExec", function(e, t) {
        if (e.command.name === "insertstring" && e.args === "." ) {
      var all = e.editor.completers;
      //e.editor.completers = [remoteCompleter];
      e.editor.execCommand("startAutocomplete");
      //e.editor.completers = all;
    }
      });
      */

      // autocomplete on 'ctrl+.'
      $scope.editor.commands.bindKey('ctrl-.', 'startAutocomplete');
      $scope.editor.commands.bindKey('ctrl-space', null);

      // handle cursor moves
      $scope.editor.keyBinding.origOnCommandKey = $scope.editor.keyBinding.onCommandKey;
      $scope.editor.keyBinding.onCommandKey = function(e, hashId, keyCode) {
        if ($scope.editor.completer && $scope.editor.completer.activated) { // if autocompleter is active
        } else {
            var numRows;
            var currentRow;
            if (keyCode === 38 || (keyCode === 80 && e.ctrlKey)) {  // UP
                numRows = $scope.editor.getSession().getLength();
                currentRow = $scope.editor.getCursorPosition().row;
                if (currentRow === 0) {
                    // move focus to previous paragraph
                    $scope.$emit('moveFocusToPreviousParagraph', $scope.paragraph.id);
                }
            } else if (keyCode === 40 || (keyCode === 78 && e.ctrlKey)) {  // DOWN
                numRows = $scope.editor.getSession().getLength();
                currentRow = $scope.editor.getCursorPosition().row;
                if (currentRow === numRows-1) {
                    // move focus to next paragraph
                    $scope.$emit('moveFocusToNextParagraph', $scope.paragraph.id);
                }
            }
        }
        this.origOnCommandKey(e, hashId, keyCode);
      };
    }
  };

  var setEditorHeight = function(id, height) {
    $('#' + id).height(height.toString() + 'px');
  };

  $scope.getEditorValue = function() {
    return $scope.editor.getValue();
  };

  $scope.getProgress = function() {
    return ($scope.currentProgress) ? $scope.currentProgress : 0;
  };

  $scope.getExecutionTime = function() {
    var pdata = $scope.paragraph;
    var timeMs = Date.parse(pdata.dateFinished) - Date.parse(pdata.dateStarted);
    if (isNaN(timeMs) || timeMs < 0) {
      return '&nbsp;';
    }
    return 'Took ' + (timeMs/1000) + ' seconds';
  };

  $scope.$on('updateProgress', function(event, data) {
    if (data.id === $scope.paragraph.id) {
      $scope.currentProgress = data.progress;
    }
  });

  $scope.$on('focusParagraph', function(event, paragraphId) {
    if ($scope.paragraph.id === paragraphId) {
      $scope.editor.focus();
      $('body').scrollTo('#'+paragraphId+'_editor', 300, {offset:-60});
    }
  });

  $scope.$on('runParagraph', function(event) {
    $scope.runParagraph($scope.editor.getValue());
  });

  $scope.$on('openEditor', function(event) {
    $scope.openEditor();
  });

  $scope.$on('closeEditor', function(event) {
    $scope.closeEditor();
  });

  $scope.$on('openTable', function(event) {
    $scope.openTable();
  });

  $scope.$on('closeTable', function(event) {
    $scope.closeTable();
  });

  $scope.$on('dependencyFilteredParagraph', function(event, paragraphId, dataFilter) {
    if ($scope.paragraph.id === paragraphId) {
      //console.info('dependencyFilteredParagraph', paragraphId, dataFilter);
      dependencyFilter = dataFilter;
      $scope.setGraphMode($scope.getGraphMode(), false, true);
    }
  });


  $scope.getResultType = function(paragraph) {
    var pdata = (paragraph) ? paragraph : $scope.paragraph;
    if (pdata.result && pdata.result.type) {
      return pdata.result.type;
    } else {
      return 'TEXT';
    }
  };

  $scope.getBase64ImageSrc = function(base64Data) {
    return 'data:image/png;base64,'+base64Data;
  };

  $scope.getGraphMode = function(paragraph) {
    var pdata = (paragraph) ? paragraph : $scope.paragraph;
    if (pdata.config.graph && pdata.config.graph.mode) {
      return pdata.config.graph.mode;
    } else {
      return 'table';
    }
  };

  $scope.isMultiChartType = function() {
    var chartType = $scope.getGraphMode();
    return chartType === 'multichart' ? true : false;
  };
  $scope.isBubbleChartType = function() {
    var chartType = $scope.getGraphMode();
    return chartType === 'bubblechart' ? true : false;
  };
  $scope.is3DBubbleChartType = function() {
    return $scope.isBubbleChartType() && !UtilService.isOrNullUndefinedEmpty($scope.paragraph.result.options.z) ? true : false;
  };

  $scope.loadTableData = function(result) {
    if (!result) {
      return;
    }
    if (result.type === 'TABLE') {
      var columnNames = [];
      var rows = [];
      var array = [];
      var textRows = result.msg.split('\n');
      result.comment = '';
      var comment = false;

      for (var i = 0; i < textRows.length; i++) {
        var textRow = textRows[i];
        if (comment) {
          result.comment += textRow;
          continue;
        }

        if (textRow === '') {
          if (rows.length>0) {
            comment = true;
          }
          continue;
        }
        var textCols = textRow.split('\t');
        var cols = [];
        var cols2 = [];
        for (var j = 0; j < textCols.length; j++) {
          var col = textCols[j];
          if (i === 0) {
            columnNames.push({name:col, index:j, aggr:'sum'});
          } else {
            cols.push(col);
            cols2.push({key: (columnNames[i]) ? columnNames[i].name: undefined, value: col});
          }
        }
        if (i !== 0) {
          rows.push(cols);
          array.push(cols2);
        }
      }
      result.msgTable = array;
      result.columnNames = columnNames;
      result.rows = rows;
    } 
    else if (result.type === 'CHART') { // json
      console.info('CHART result.msg', result.msg);
      var obj = JSON.parse(result.msg);
      var columnNames = [];
      var rows = [];
      var array = [];
      var colIndex = 0;
      if(obj.datas.length > 0) {
        columnNames.push({name:'rowNumIndexColumn', index:colIndex, aggr:'sum'});
        for(var colunmName in obj.datas[0]) {
          colIndex++;
          columnNames.push({name:colunmName, index:colIndex, aggr:'sum'});
        }
      }
      for (var rowIndex in obj.datas) {
        var cols = [];
        var cols2 = [];
        for(var i=0 ; i<columnNames.length ; i++) {
          var col = obj.datas[rowIndex][columnNames[i].name] === undefined ? rowIndex : obj.datas[rowIndex][columnNames[i].name];
          cols.push(col);
          cols2.push({key: (columnNames[i]) ? columnNames[i].name: undefined, value: col});
        }
        rows.push(cols);
        array.push(cols2);
      }
      result.msgTable = array;
      result.columnNames = columnNames;
      result.rows = rows;
      
      //어떤종류의 차트인지 구분값
      result.options = obj.options;
      result.options.key = UtilService.isOrNullUndefinedEmpty(result.options.key) ? rowIndexKeyColumnName : result.options.key;
      result.mode = UtilService.isOrNullUndefinedEmpty(obj.type) ? 'timechart' : obj.type;
      
      if(result.mode === 'bubblechart') {
        result.options.key = result.options.x;
        result.options.value = result.options.y;
        //result.options.z = result.options.z;
        //result.options.size = result.options.size;
      }

//      start of test code
//      result.mode = (obj.type === 'timeseries' ? 'timechart' : obj.type);
//      result.options = {key:'age', value:'userCnt',group:'gender', min:'minBalance', max:'maxBalance', expected:''};
//      
//      result.mode = 'timechart'; //필수:key, value
//      result.options = {key:'age', value:'userCnt',group:'', min:'', max:'', expected:'maxBalance'};
//      
//      result.mode = 'barchart'; //필수:key, value
//      result.options = {key:'age', value:'userCnt',group:'gender', min:'', max:'', expected:''};
//      
//      result.mode = 'piechart'; //필수:key, value
//      result.options = {key:'age', value:'userCnt',group:'', min:'', max:'', expected:''};
//            
      // result.mode = 'bubblechart'; //필수:x, y, size, z(z가 있으면 3D 로 표현)
      // result.options = {x:'age', y:'userCnt', z:'', group:'', size:'userCnt'};
      // result.options.key = result.options.x;
      // result.options.value = result.options.y;
            
      // result.mode = 'mapchart'; //필수:key, value, latitude(위도:가로), longitude(경도:세로)
      // result.options = {value:'Field3',latitude: 'Field13', longitude:'Field14'};
       
      if($scope.paragraph.id === '20150728-065616_1767041320') { //멀티차트 테스트
        result.mode = 'multichart'; 
        result.options = {key:'age', value:{bar:'maxBalance', time:['minBalance', 'userCnt']}};
        //result.options = {key:'age', value:{bar:'maxBalance', time:['minBalance', 'userCnt']}, group:'gender'};
      }
//      end of test code
    }
  };

  $scope.setGraphMode = function(type, emit, refresh) {
    if (emit) {
      setNewMode(type);
    } else {
      clearUnknownColsFromGraphOption();
      // set graph height
      var height = $scope.paragraph.config.graph.height;
      $('#p'+$scope.paragraph.id+'_graph').height(height);

      if (!type || type === 'table') {
        filteredParagraphResult = getParagraphResult($scope.paragraph.result);
        setTable(filteredParagraphResult, refresh);
      }
      else if (type === 'timechart' || type === 'barchart' || type === 'piechart' || type === 'bubblechart' || type === 'multichart') {
        filteredParagraphResult = getParagraphResult($scope.paragraph.result);
        setHighChart(type, filteredParagraphResult, refresh);
        setSortTablefilterRowsByArray();
      } 
      else if (type === 'mapchart') {
        filteredParagraphResult = getParagraphResult($scope.paragraph.result);
        setMapChart(type, filteredParagraphResult, refresh); 
        setSortTablefilterRowsByArray();       
      }
      else {
        setD3Chart(type, $scope.paragraph.result, refresh);
      }
    }
  };
  
  /** 마스터에 필터가 존재하는 경우 필터링한 데이타를 기준으로 화면처리한다. */
  var getParagraphResult = function(scopeParagraphResult) {
    if(dependencyFilter === undefined) {
      return scopeParagraphResult;
    }
    var scopeResult = angular.copy(scopeParagraphResult);

    var colIndex = 0;
    for(var index in scopeResult.columnNames) {
      if(dependencyFilter.columnName === scopeResult.columnNames[index].name) {
        colIndex = scopeResult.columnNames[index].index;
      }
    }
    var msgTable = [];
    var rows = [];
    for (var rowIndex in scopeResult.msgTable) {
      var isFiltered = false;
      if(dependencyFilter.filters.indexOf(scopeResult.msgTable[rowIndex][colIndex].value) > -1) {
        rows.push(scopeResult.rows[rowIndex]);
        msgTable.push(scopeResult.msgTable[rowIndex]);
      }
    }
    scopeResult.msgTable = msgTable;
    scopeResult.rows = rows;
    return scopeResult;
  };

  var setSortTablefilterRowsByArray = function(filters) {
    var tableDivId = 'p' + $scope.paragraph.id + '_timetable';
    
    var renderTable = function() {
      if(filters === undefined) {
        RawTable.buildTable(tableDivId);
        $scope.paragraph.config.dataFilter = undefined;
      } else {
        var columnIndex = $scope.paragraph.config.graph.keys[0].index;
        RawTable.applyFilters(tableDivId, columnIndex, filters);
        $scope.paragraph.config.dataFilter = {columnName: $scope.paragraph.config.graph.keys[0].name, filters: filters};
      }
      $scope.refreshDependencyFilteredParagraphs($scope.paragraph.id, $scope.paragraph.config.dataFilter);
    };

    var retryRenderer = function() {
      try {
        renderTable();
      } catch(err) {
        console.log('Chart drawing error %o', err);
      }
    };
    $timeout(retryRenderer);
  };

  var setHighChart = function(type, data, refresh) {
console.info('data', data);
      
    var renderChart = function() {
      if (!refresh) {
        //optionOpen 이 변경된경우, refresh 가 false 인경우 차트를 재생성 하지 않는다.
        return;
      }
      var height = $scope.paragraph.config.graph.height;
      $('#p'+$scope.paragraph.id+'_'+type).css('height', height);

      var p = pivot(data, true);
      //console.info('p', p);
      
      var getChartType = function(type) {
        var chartType = 'line';
        if(type === undefined) {
          return chartType;
        }
        switch(type) {
          case 'timechart' : 
          case 'time' :
          case 'line' : chartType = 'line'; break;
          case 'barchart'  : 
          case 'bar'  : 
          case 'column' :chartType = 'column'; break;
          case 'piechart'  : 
          case 'pie'  : chartType = 'pie'; break;
          case 'bubblechart'  : 
          case 'scatter'  : chartType = 'scatter'; break;
        };
        return chartType;
      };

      var chartType = getChartType(type);
      console.info('type', type,'chartType', chartType);
      
      //key (x축) 데이터 성격에 따라서 x축의 타입을 결정한다.
      var xAxisType = null; //'category', //'linear', //'datetime',
      if(data.rows !== undefined && data.rows.length > 0) {
        var keyValue = data.rows[0][$scope.paragraph.config.graph.keys[0].index];
        //console.info('keyValue', keyValue);
        xAxisType = (function(keyValue) {
          if (angular.isDate(keyValue)) {
            return 'datetime';
          }
          if(!isNaN(keyValue)) {
            return 'linear';
          } else {
            console.info('not isNaN(keyValue)');
          }
          
          return 'category';
        })(keyValue);
      }
      //console.info('xAxisType', xAxisType);
      
      //analysis value , result.options = {value:{bar:'maxBalance', time:['minBalance']};
      //multichart 의 value 옵션에 대한 정보를 추출한다.
      var getValueInfos = function(optionsValue) {
        var valueInfos = [];
        //var value = $scope.paragraph.result.options.value;
        if(optionsValue instanceof Object) {
          for(var name in optionsValue) {
            var obj = optionsValue[name];
            if(obj instanceof Array) {
              for(var i=0 ; i<obj.length ; i++) {
                var valueInfo = {columnName: obj[i], chartType: name};
                valueInfos.push(valueInfo);
              }
            } else if(typeof obj === "string" || obj instanceof String) {
              var valueInfo = {columnName: obj, chartType: name};  
              valueInfos.push(valueInfo);
            }
          }
        } else {
          var valueInfo = {columnName: optionsValue, chartType: chartType};  
          valueInfos.push(valueInfo);
        }
        return valueInfos;
      };

      //make series data
      //otionsValue 의 배열만큼 series 를 생성하고, group 만큼 배수가 된 series 정보를 생성한다.
      //values 가 존재하면 group 와 카테시안 곱으로 결합된 series 정보를 갖는다.
      //values 로 정의된 series 가 없는 경우 group 로 정의된 것만 series 정보를 갖는다.
      var getSeriesInfos = function(schema, optionsValue) {        
        var keys = Object.keys(schema);
        var seriesInfos = [];
        var labels = [];
        for (var prop in keys) {
          var children = schema[keys[prop]].children;
          var childKeys = Object.keys(children);
          for(var k in childKeys) {
            if(children[childKeys[k]].type === 'group') {
              labels.push({groupName: childKeys[k], name: childKeys[k], chartType: chartType});
            }
          }
          var values = getValueInfos(optionsValue);
          for(var i=0 ; i<values.length ; i++) {
            if(labels.length > 1) { //그룹정보가 2개이상인경우, 결합 series name 을 갖는다.
              for(var k=0 ; k<labels.length ; k++) {
                seriesInfos.push({
                  groupName: labels[k].name,
                  name: (values.length > 1) ? labels[k].name+'_'+values[i].columnName : labels[k].name,
                  chartType: values[i].columnName === undefined ? chartType : getChartType(values[i].chartType),
                  valueColumnName: values[i].columnName
                });
              }
            } else {
              seriesInfos.push({
                groupName: values[i].columnName,
                name: values[i].columnName, 
                chartType: values[i].columnName === undefined ? chartType : getChartType(values[i].chartType),
                valueColumnName: values[i].columnName
              });
            }
          }
          if(seriesInfos.length == 0) {
            for(var k=0 ; k<labels.length ; k++) {
              labels[k].valueColumnName = optionsValue;
            }
            seriesInfos = labels;
          }
        }
        return seriesInfos;
      };

      var setSeriesDataByRow = function(seriesInfo, row, x, mainSeries, subSeries, rangeSeries) {
        if(UtilService.isUndefined(row)) {
          return;
        }
        //console.info('seriesInfo', seriesInfo, 'row', row);
        if(!UtilService.isOrNullUndefinedEmpty($scope.paragraph.result.options.value)) {
          var sname = seriesInfo.name + '_' + seriesInfo.chartType;
          mainSeries[sname] = mainSeries[sname] || {name: seriesInfo.name, data:[], type: seriesInfo.chartType};
          //category 에서는 x 축을 name 에서 얻는다. x property 를 주면 안됨
          var data = {name: x,
                      //x: x, 
                      y: Number(row[seriesInfo.valueColumnName].value), 
                      z: Number(!UtilService.isOrNullUndefinedEmpty($scope.paragraph.result.options.z) ? row[$scope.paragraph.result.options.z].value : 0),
                      size: Number(!UtilService.isOrNullUndefinedEmpty($scope.paragraph.result.options.size) ? row[$scope.paragraph.result.options.size].value : 1)
                    };
          if(xAxisType !== 'category') {
            data.x = x;
          }
          mainSeries[sname].data.push(data);
        }
        if(!UtilService.isOrNullUndefinedEmpty($scope.paragraph.result.options.min) && !UtilService.isOrNullUndefinedEmpty($scope.paragraph.result.options.max)) {
          var sname = seriesInfo.name;
          rangeSeries[sname] = rangeSeries[sname] || [];
          rangeSeries[sname].push({x: x, 
                                   low: Number(row[$scope.paragraph.result.options.min].value), 
                                   high: Number(row[$scope.paragraph.result.options.max].value)
                                 });
        }
        if(!UtilService.isOrNullUndefinedEmpty($scope.paragraph.result.options.expected)) {
          var sname = seriesInfo.name;
          subSeries[sname] = subSeries[sname] || [];
          subSeries[sname].push({name: x,
                                 x: x, 
                                 y: Number(row[$scope.paragraph.result.options.expected].value)
                               });
        }
      }
      var seriesInfos = getSeriesInfos(p.schema, $scope.paragraph.result.options.value);
      //console.info('seriesInfos', seriesInfos);
      var mainSeries = {};
      var subSeries = {};
      var rangeSeries = {};
      if(!UtilService.isOrNullUndefinedEmpty($scope.paragraph.result.options.group)) {
        for(var s in seriesInfos) {
          var seriesInfo = seriesInfos[s];
          for(var x in p.rows) {
            var row = p.rows[x][seriesInfo.groupName];
            setSeriesDataByRow(seriesInfo, row, x, mainSeries, subSeries, rangeSeries);
          }
        }
      } else {
        for(var s in seriesInfos) {
          var seriesInfo = seriesInfos[s];
          for(var x in p.rows) {
            var row = p.rows[x];
            setSeriesDataByRow(seriesInfo, row, x, mainSeries, subSeries, rangeSeries);
          }
        }
      } 
      //console.info('mainSeries', mainSeries);
      //console.info('subSeries', subSeries);
      //console.info('rangeSeries', rangeSeries);

      //make series
      var series = [];
      var colorIndex = 0;
      var colorOpacity = 0.75;
      for(var sname in mainSeries) {
        var rgba = new Highcharts.Color(Highcharts.getOptions().colors[colorIndex++]).setOpacity(colorOpacity).get();
        var seriesOption = {
          id : mainSeries[sname].name,  //linkedTo : sname, 속성과 연결
          name : mainSeries[sname].name,
          data : mainSeries[sname].data,
          type : mainSeries[sname].type,
          zIndex : 1,
          marker : {
            enabled: false
          },
          color : rgba,
          lineWidth: 1
        };
        if($scope.isMultiChartType()) {
          seriesOption.yAxis = series.length;
        }
        if($scope.isBubbleChartType()) {
          var lineColor = '#000000';
          var fillColor = {
            radialGradient: { cx: 0.4, cy: 0.3, r: 0.7 },
            stops: [
              [0, 'rgba(255,255,255,1)'],
              [1, rgba]
            ]
          };
          seriesOption.marker.enabled = true;
          seriesOption.marker.symbol = 'circle';
          seriesOption.marker.fillColor = fillColor;
          seriesOption.lineWidth = 0;
          if(UtilService.isOrNullUndefinedEmpty($scope.paragraph.result.options.size)) {
            seriesOption.maxSize = "10";
          }
          if(!UtilService.isOrNullUndefinedEmpty($scope.paragraph.result.options.size)) {
            angular.forEach(seriesOption.data, function(data, i){
              data.marker = {
                radius: data.size,
              };
              data.marker.states = {
                hover: {
                  lineColor: lineColor,
                  fillColor: fillColor,
                  radius: data.size
                },
                select: {
                  lineColor: lineColor,
                  fillColor: fillColor,
                  radius: data.size,
                  enabled: true
                }
              };
            });
          }
          seriesOption.tooltip = {
            pointFormat: 'x: <b>{point.x}</b><br/>'
                        +'y: <b>{point.y}</b><br/>' 
                        + ($scope.is3DBubbleChartType() ? 'z: <b>{point.z}</b><br/>' : '')
                        +'size: <b>{point.marker.radius}</b><br/>'
          };
        }
        series.push(seriesOption);
      }
      for(var sname in rangeSeries) {
        series.push({
          id : 'range_' + sname,
          name : 'range_' + sname,
          data : rangeSeries[sname],
          type : 'arearange',
          lineWidth : 0,
          linkedTo : sname,
          fillOpacity : 0.3,
          zIndex : 0
        });
      }
      colorIndex = 0;
      for(var sname in subSeries) {
        var rgba = new Highcharts.Color(Highcharts.getOptions().colors[colorIndex++]).setOpacity(colorOpacity).get();
        var seriesOption = {
          id : 'sub_' + sname,
          name : 'sub_' + sname,
          data : subSeries[sname],
          type : chartType,
          zIndex : 1,
          dashStyle : 'LongDashDot',
          linkedTo : sname,
          marker : {
            enabled: false
          },
          color : rgba,
          lineWidth: 1
        };
        if($scope.isBubbleChartType()) {
          seriesOption.lineWidth = 0;
        }
        series.push(seriesOption);
      }
      //console.info('series', series);
      var legendEnable = chartType === 'pie' ? true : seriesInfos.length <= 1 ? false : true;
      //console.info('$scope.paragraph.config.label', $scope.paragraph.config.label);
      
      //차트 라벨 설정
      var initLabelY = [];
      if($scope.isMultiChartType()) {
        for(var i=0 ; i<series.length ; i++) {
          initLabelY.push(series[i].name);
        }
      } else {
        initLabelY.push($scope.paragraph.config.graph.values.length > 0 ? $scope.paragraph.config.graph.values[0].name : '');
      }
      //console.info('initLabelY', initLabelY);
      var labelTitle = !UtilService.isOrNullUndefinedEmpty($scope.paragraph.config.label.title) ? $scope.paragraph.config.label.title : '';
      var labelX = !UtilService.isOrNullUndefinedEmpty($scope.paragraph.config.label.x) 
                    ? $scope.paragraph.config.label.x : $scope.paragraph.config.graph.keys.length > 0 ? $scope.paragraph.config.graph.keys[0].name : '';
      var labelY = !UtilService.isOrNullUndefinedEmpty($scope.paragraph.config.label.y) && $scope.paragraph.config.label.y.length === initLabelY.length
                    ? $scope.paragraph.config.label.y : initLabelY;
      var labelZ = !UtilService.isOrNullUndefinedEmpty($scope.paragraph.config.label.z) 
                    ? $scope.paragraph.config.label.z : !UtilService.isOrNullUndefinedEmpty($scope.paragraph.result.options.z) ? $scope.paragraph.result.options.z : '';
      $scope.paragraph.config.label.title = labelTitle;
      $scope.paragraph.config.label.x = labelX;
      $scope.paragraph.config.label.y = labelY;
      $scope.paragraph.config.label.z = labelZ;
      //console.info('labelY', labelY);
      //console.info('legendEnable', legendEnable);
      /**
       * Highcharts plugin for setting a lower opacity for other series than the one that is hovered
       * in the legend
       */
      (function(Highcharts) {
        var each = Highcharts.each;
        var initLegendElement = function(chart, item) {
          var isPoint = !!item.series, 
              collection = isPoint ? item.series.points : chart.series, 
              groups = isPoint ? [ 'graphic' ] : [ 'group', 'markerGroup' ], 
              element = item.legendGroup.element,
              mouseoutEffect = [{opacity : 0.25}, {duration : 150}],
              mouseoverEffect = [{opacity : 1}, {duration : 50}],
              mouseOverFn = function() {
                each(collection, function(seriesItem) {
                  if (seriesItem !== item && seriesItem.linkedParent === undefined) {
                    each(groups, function(group) {
                      seriesItem[group].animate(mouseoutEffect[0], mouseoutEffect[1]);
                    });
                    if(seriesItem.linkedSeries !== null && seriesItem.linkedSeries !== undefined) {
                      each(seriesItem.linkedSeries, function(linkedSeriesItem) {
                        each(groups, function(group) {
                          linkedSeriesItem[group].animate(mouseoutEffect[0], mouseoutEffect[1]);
                        });
                      });
                    }
                  } else if (seriesItem === item && seriesItem.linkedParent === undefined && seriesItem['graph'] !== undefined) {
                    seriesItem['graph'].css({'stroke-width': 2});
                  }
                });
              },
              mouseOutFn = function() {
                each(collection, function(seriesItem) {
                  if (seriesItem !== item && seriesItem.linkedParent === undefined) {
                    each(groups, function(group) {
                      seriesItem[group].animate(mouseoverEffect[0], mouseoverEffect[1]);
                    });
                    if(seriesItem.linkedSeries !== null && seriesItem.linkedSeries !== undefined) {
                      each(seriesItem.linkedSeries, function(linkedSeriesItem) {
                        each(groups, function(group) {
                          linkedSeriesItem[group].animate(mouseoverEffect[0], mouseoverEffect[1]);
                        });
                      });
                    }
                  } else if (seriesItem === item && seriesItem.linkedParent === undefined && seriesItem['graph'] !== undefined) {
                    seriesItem['graph'].css({'stroke-width': 1});
                  }
                });
              }
              ;
          element.onmouseover = mouseOverFn;
          element.onmouseout = mouseOutFn;    
        };
            
        Highcharts.wrap(Highcharts.Legend.prototype, 'renderItem', function(proceed, item) {
          proceed.call(this, item);
          initLegendElement(this.chart, item);
        });
      }(Highcharts));
       
      // new highchart
      var chartOption = {        
        chart: {
          renderTo: 'p' +$scope.paragraph.id+'_'+type,
          type: chartType,
          //zoomType: $scope.isBubbleChartType() ? undefined : 'x',
          zoomType: 'x',
          events: {
            selection : function(event) {
              var thisSeries = this.series;
              angular.forEach(thisSeries, function(series, k) {
                angular.forEach(series.data, function(data, i) {
                  data.select(false);
                });              
              });
              
              if (event.xAxis) {
                var xAxis = event.xAxis[0];
                var xMin = Math.round(xAxis.min);
                var xMax = Math.round(xAxis.max);
                xMin = xMin < xAxis.axis.dataMin ? xAxis.axis.dataMin : xMin;
                xMax = xMax > xAxis.axis.dataMax ? xAxis.axis.dataMax : xMax;
                setTimeout(function() {
                  var filters = [];
                  angular.forEach(thisSeries, function(series, k) {
                    angular.forEach(series.data, function(data, i) {
                      //console.info('xMin', xMin, 'xMax', xMax, 'i', i, 'data.x', data.x, 'data.name', data.name);
                      if (xMin <= data.x && data.x <= xMax) {
                        filters.push(data.name);
                        data.select(true, true);
                      }
                    }, filters);
                  });
                  $scope.$apply(function() {
                    setSortTablefilterRowsByArray(filters);
                  });
                }, 100);
                return true;
              } else {
                $scope.$apply(function() { // 외부 라이브러리의 이벤트에서 호출될시 $apply 를 적용한다.
                  setSortTablefilterRowsByArray();
                });
                //console.info('select reset zoom');
              }
            }
          }
        },
        title : {
          text : $scope.paragraph.config.label.title,
          style : { 
            color: '#333333',
            fontWeight : 'bold'
          } 
        },
        xAxis : {
          type : xAxisType, //'category', //'linear', //'datetime',
          title : {
            text : $scope.paragraph.config.label.x,
            style : { 
              color: '#333333',
              fontWeight : 'bold'
            }
          },
          gridLineWidth : 1
        },
        yAxis : [{
          min: 0,
          title : {
            text : $scope.paragraph.config.label.y[0],
            style : { 
              color: '#333333',
              fontWeight : 'bold'
            }
          }
        }],
        tooltip : {
          crosshairs : true,
          shared : true,
        },
        legend : {
          enabled : chartType === 'pie' ? true : seriesInfos.length <= 1 ? false : true, 
          align : 'left',
          verticalAlign : chartType === 'pie' ? 'middle' : 'top',
          layout : 'vertical',
          floating : chartType === 'pie' ? false : true,
          x : 100
        },
        plotOptions: {
          pie: {
            allowPointSelect: true,
            states: {
              select: {
                color: '#fa965f'
              }
            },
            cursor: 'pointer',
            showInLegend: true
          },
          column: {
            allowPointSelect: true,
            states: {
              select: {
                color: '#fa965f'
              }
            }
          },
          series: {
            events : {
              click : function(event) {
                var thisSeries = this.chart.series;
                setTimeout(function() {
                  var filters = [];
                  angular.forEach(thisSeries, function(series, k) {
                    angular.forEach(series.data, function(data, i) {
                      if (data.selected === true) {
                        filters.push(data.name);
                      }
                    }, filters);
                  });
                  $scope.$apply(function() {
                    setSortTablefilterRowsByArray(filters);
                  });
                }, 100);   
              }
            }
          }
        },
        credits: {
          enabled: false
        },
        series : series
      };
      if($scope.is3DBubbleChartType()) {
        //3D
        chartOption.chart.panKey = 'shift'; //3d차트의 시점이동을 shift 키로 하기위해서, shift 일때는 범위선택을 하지 못하게 하기위함

        chartOption.chart.options3d = {
          enabled: true,
          alpha: 10,
          beta: 0,
          depth: 250,
          viewDistance: 5,
          frame: {
              bottom: { size: 1, color: 'rgba(0,0,0,0.02)' },
              back: { size: 1, color: 'rgba(0,0,0,0.04)' },
              side: { size: 1, color: 'rgba(0,0,0,0.06)' }
          }
        };
        chartOption.zAxis = {
          min: 0,
          title : {
            text : $scope.paragraph.config.label.z,
            style : { 
              color: '#333333',
              fontWeight : 'bold'
            }
          }
        };
      }
      if($scope.isMultiChartType()) {
        chartOption.yAxis = [];
        var i = 0;
        for(var prop in mainSeries) {
          chartOption.yAxis.push({
            min: 0,
            title : {
              text : $scope.paragraph.config.label.y[i],
              style : { 
                color: Highcharts.getOptions().colors[i],
                fontWeight : 'bold'
              }
            },
            opposite: i == 0 ? false : true
          });
          i++;
        }
      }
      console.info('chartOption',chartOption);
      var chart = new Highcharts.Chart(chartOption);

      if(chartOption.chart.options3d !== undefined) {
        $(chart.container).bind('keydown.hc mousedown.hc touchstart.hc', function (e) {
          e = chart.pointer.normalize(e);
          var posX = e.pageX,
              posY = e.pageY,
              alpha = chart.options.chart.options3d.alpha,
              beta = chart.options.chart.options3d.beta,
              newAlpha,
              newBeta,
              sensitivity = 5; // lower is more sensitive
          $(document).bind({
            'keydown.hc mousemove.hc touchdrag.hc': function (e) {
              //keydown 일때 shift 키 이면 처리하지않고, 그다음 mousemove 키가 눌렸을때 3D 시야이동 작동
              if(e.shiftKey === false) {
                return false;
              }
              if(e.type === 'keydown') {
                return false;
              }
              // Run beta
              newBeta = beta + (posX - e.pageX) / sensitivity;
              chart.options.chart.options3d.beta = newBeta;

              // Run alpha
              newAlpha = alpha + (e.pageY - posY) / sensitivity;
              chart.options.chart.options3d.alpha = newAlpha;

              chart.redraw(false);
            },
            'keyup mouseup touchend': function () {
                $(document).unbind('.hc');
            }
          });
        });
      }

      if($scope.labelWatchFun) {
        $scope.labelWatchFun(); // it will destroy your previous $watch if any exist
      }
      $scope.labelWatchFun = $scope.$watch("paragraph.config.label", function (newValue, oldValue) {
        //console.info(newValue, oldValue);
        if(newValue) {
          chart.setTitle({text: newValue.title}, null, false);
          chart.xAxis[0].setTitle({text: newValue.x}, false);
          if(newValue.y instanceof Array) {
            for(var i=0 ; i<newValue.y.length ; i++) {
              chart.yAxis[i].setTitle({text: newValue.y[i]}, false);
            }
          } else {
            chart.yAxis[0].setTitle({text: newValue.y}, false);
          }
          if($scope.is3DBubbleChartType()) {
            chart.zAxis[0].setTitle({text: newValue.z}, false);
          }
          chart.redraw(false);
        }
      }, true);

      var maxScaleDataValue = 0;
      for(var sname in mainSeries) {
        angular.forEach(mainSeries[sname].data, function(item, index) {
          if(item.y > maxScaleDataValue) {
            maxScaleDataValue = item.y;
          }
        });
      }
      //console.info('maxScaleDataValue', maxScaleDataValue);
      if($scope.autoscaleWatchFun) {
        $scope.autoscaleWatchFun(); // it will destroy your previous $watch if any exist
      }
      $scope.autoscaleWatchFun = $scope.$watch("paragraph.config.autoscale", function (newValue, oldValue) {
        //console.info(newValue, oldValue);
        if(newValue) {
          for(var i in newValue.y) {
            console.info('newValue.y[i]', newValue.y[i]);
            if(newValue.y[i] === true) {
              chart.yAxis[i].setExtremes(0, maxScaleDataValue, false);
            } else {
              chart.yAxis[i].setExtremes(0, undefined, false);
            }
          }
          chart.redraw(true);
        }
      }, true);
    };
    var retryRenderer = function() {
      if ($('#p'+$scope.paragraph.id+'_'+type).length !== 0) {
        try {
          renderChart();
        } catch(err) {
          console.log('Chart drawing error %o', err);
        }
      } else {
        $timeout(retryRenderer,10);
      }
    };
    $timeout(retryRenderer);
  };
  
  
  var setMapChart = function(type, data, refresh) {
    var renderChart = function() {
      if (!refresh) {
        return;
      }
      var height = $scope.paragraph.config.graph.height;
      $('#p'+$scope.paragraph.id+'_'+type).css('height', height);

      var p = pivot(data, true);
      //console.info('p', p);
      
      var mapOptions = {
        zoom: 10,
        center: new google.maps.LatLng(37.380563100887834, 127.11582362651825), 
        maxZoom: 20,
        minZoom: 7
      }
      var map = new google.maps.Map(document.getElementById('p'+$scope.paragraph.id+'_'+type), mapOptions);
      // var controlDiv = document.getElementById('p'+$scope.paragraph.id+'_mapcontrol');
      // google.maps.event.addDomListener(controlDiv, 'click', function() {
      //   console.info('click controlDiv');
      // });
      // controlDiv.index = 1;
      // map.controls[google.maps.ControlPosition.TOP_RIGHT].push(controlDiv);
      // $scope.toggleMapOptionView = function() {
      //   if($scope.showMapOptionView) {
      //     $scope.showMapOptionView = false;
      //   } else {
      //     $scope.showMapOptionView = true;
      //   }
      // };
      // $scope.mouseoutMapOptionView = function() {
      //   setTimeout(function() {
      //     $scope.showMapOptionView = false;
      //   } ,1000);
      // };

      //heatmaplayer
      var heatmapData = (function(p) {
        var datas = [];
        for(var x in p.rows) {
          var row = p.rows[x];
          var latitude = 0;
          var longitude = 0;   
          var value = 0;
          if(!UtilService.isOrNullUndefinedEmpty($scope.paragraph.result.options.latitude)) {
            latitude = row[$scope.paragraph.result.options.latitude].value;
          }
          if(!UtilService.isOrNullUndefinedEmpty($scope.paragraph.result.options.longitude)) {
            longitude = row[$scope.paragraph.result.options.longitude].value;
          }
          if(!UtilService.isOrNullUndefinedEmpty($scope.paragraph.result.options.value)) {
            value = Number(row[$scope.paragraph.result.options.value].value);
          } 
          var latLng = new google.maps.LatLng(latitude, longitude);
          datas.push({location:latLng, weight: value});
        }
        return datas;
      })(p);
      var maxIntensity = 0;
      angular.forEach(heatmapData, function(item, i){
        maxIntensity = item.weight > maxIntensity ? item.weight : maxIntensity;
      }); 
      console.info('maxIntensity', maxIntensity);  
      var heatmap = new google.maps.visualization.HeatmapLayer({
        data: heatmapData,
        radius: 20,
        //dissipating: true,
        maxIntensity: maxIntensity/2,
        //opacity: 0.6,
        map: map
      });
        
     // setInterval(function() {
      //   var mvcArray = heatmap.getData();
      //   angular.forEach(heatmapData(), function(item, i) {
      //     this.push(item);
      //   }, mvcArray);
      //   heatmap.setData(mvcArray);
      // },1000);

      //marker
      // for(var x in p.rows) {
      //   var row = p.rows[x];
      //   var latitude = 0;
      //   var longitude = 0;   
      //   var value = 0;
      //   if(!UtilService.isOrNullUndefinedEmpty($scope.paragraph.result.options.latitude)) {
      //     latitude = row[$scope.paragraph.result.options.latitude].value;
      //   }
      //   if(!UtilService.isOrNullUndefinedEmpty($scope.paragraph.result.options.longitude)) {
      //     longitude = row[$scope.paragraph.result.options.longitude].value;
      //   }
      //   if(!UtilService.isOrNullUndefinedEmpty($scope.paragraph.result.options.value)) {
      //     value = row[$scope.paragraph.result.options.value].value;
      //   } 
      //   var latLng = new google.maps.LatLng(latitude, longitude);

      //   var marker = new google.maps.Marker({
      //     position: latLng,
      //     map: map,
      //     icon: {
      //       path: google.maps.SymbolPath.CIRCLE,
      //       scale: Number(value),
      //       fillColor: '#08B21F',
      //       fillOpacity: 0.5,
      //       strokeColor: '#08B21F',
      //       strokeOpacity: 1,
      //       strokeWeight: 1        
      //     },
      //     title: latitude + ', ' + longitude
      //   });
      //   var content = ''+
      //   '<div>'+
      //   '  <div>scale: ' + Number(value) + '<div>'+
      //   '  <div>위도 : ' + latitude + '<div>'+
      //   '  <div>경도 : ' + longitude + '<div>'+
      //   '</div>';
      //   var infowindow = new google.maps.InfoWindow();
      //   google.maps.event.addListener(marker,'click', (function(marker,content,infowindow){ 
      //     return function() {
      //       infowindow.setContent(content);
      //       infowindow.open(map,marker);
      //     };
      //   })(marker,content,infowindow));  
      // }
      google.maps.event.addListener(map, 'zoom_changed', function() {
        var zoomLevel = map.getZoom();
        //heatmap.setOptions({radius: zoomLevel *4});
        //console.info('zoomLevel', zoomLevel, 'heatmap.radius', heatmap.radius);
      });
      

    };
    var retryRenderer = function() {
      if ($('#p'+$scope.paragraph.id+'_'+type).length !== 0) {
        try {
          renderChart();
        } catch(err) {
          console.log('Chart drawing error %o', err);
        }
      } else {
        $timeout(retryRenderer,10);
      }
    };
    $timeout(retryRenderer);
  };
  
  
  ////////////////////////

  var setNewMode = function(newMode) {
    var newConfig = angular.copy($scope.paragraph.config);
    var newParams = angular.copy($scope.paragraph.settings.params);

    // graph options
    newConfig.graph.mode = newMode;

    commitParagraph($scope.paragraph.title, $scope.paragraph.text, newConfig, newParams);
  };

  var commitParagraph = function(title, text, config, params) {
    var parapgraphData = {
      op: 'COMMIT_PARAGRAPH',
      data: {
        id: $scope.paragraph.id,
        title : title,
        paragraph: text,
        params: params,
        config: config
      }};
    $rootScope.$emit('sendNewEvent', parapgraphData);
  };

  var RawTable = {
    getTableContentFormat : function(d) {
      if (isNaN(d)) {
        if (d.length>'%html'.length && '%html ' === d.substring(0, '%html '.length)) {
          return 'html';
        } else {
          return '';
        }
      } else {
        return '';
      }
    },
    formatTableContent : function(d) {
      if (isNaN(d)) {
        var f = this.getTableContentFormat(d);
        if (f !== '') {
          return d.substring(f.length+2);
        } else {
          return d;
        }
      } else {
        var dStr = d.toString();
        var splitted = dStr.split('.');
        var formatted = splitted[0].replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1,');
        if (splitted.length>1) {
          formatted+= '.'+splitted[1];
        }
        return formatted;
      }
    },
    buildTable : function(tableDivId) {
      var html = '<table class="table table-striped table-condensed"></table>';
      $('#' + tableDivId).html(html);
      
      var columns = [];
      for (var c in filteredParagraphResult.columnNames) {
        if(filteredParagraphResult.columnNames[c].name === rowIndexKeyColumnName) {
          continue;
        }
        columns.push({
          data: filteredParagraphResult.columnNames[c].name, 
          title:filteredParagraphResult.columnNames[c].name
        });
      }
      var data = [];
      for (var r in filteredParagraphResult.msgTable) {
        var d = {};
        var row = filteredParagraphResult.msgTable[r];
        for (var index in row) {
          if(row[index].key === rowIndexKeyColumnName) {
            continue;
          }
          var v = row[index].value;
          if (this.getTableContentFormat(v) !== 'html') {
            v = v.replace(/[\u00A0-\u9999<>\&]/gim, function(i) {
                return '&#'+i.charCodeAt(0)+';';
            });
          }
          d[filteredParagraphResult.columnNames[index].name] = this.formatTableContent(v);
        }
        data.push(d);
      }

      var height = $scope.paragraph.config.graph.heightTable || $scope.paragraph.config.graph.height;
      $('#' + tableDivId + ' table').DataTable({
        paging: false,
        searching: true,
        sDom: 'rt<i>',
        scrollY: height,
        data: data,
        columns: columns
      });
      this.setTableHight(tableDivId);
    },
    applyFilters : function(tableDivId, columnIndex, filters) {
      var searchValues = '';
      angular.forEach(filters, function(item, i) {
        searchValues += '((^|,)' + RawTable.formatTableContent(item) + '($|,))|';
      });
      searchValues = searchValues.replace(/\|$/g, '');
      console.info('columnIndex-1', (columnIndex-1), 'searchValues', searchValues);
      var table = $('#' + tableDivId + ' table').DataTable();
      table.column(columnIndex-1).search(searchValues, true, false).draw();
    },
    setTableHight : function(tableDivId) {
      var height = $scope.paragraph.config.graph.heightTable || $scope.paragraph.config.graph.height;
      $('#' + tableDivId).height(height);
      $('#' + tableDivId + ' div.dataTables_scrollBody').height(height - 67);
    }
  };

  var setTable = function(type, data, refresh) {
    var tableDivId = 'p' + $scope.paragraph.id + '_table';

      var renderTable = function() {
      RawTable.buildTable(tableDivId);
    };

    var retryRenderer = function() {
      if ($('#' + tableDivId).length) {
        try {
          renderTable();
        } catch(err) {
          console.log('Chart drawing error %o', err);
        }
      } else {
        $timeout(retryRenderer,10);
      }
    };
    $timeout(retryRenderer);

  };

  var setD3Chart = function(type, data, refresh) {
    if (!$scope.chart[type]) {
      var chart = nv.models[type]();
      $scope.chart[type] = chart;
    }

    var d3g = [];

    if (type === 'scatterChart') {
      var scatterData = setScatterChart(data, refresh);

      var xLabels = scatterData.xLabels;
      var yLabels = scatterData.yLabels;
      d3g = scatterData.d3g;

      $scope.chart[type].xAxis.tickFormat(function(d) {
        if (xLabels[d] && (isNaN(parseFloat(xLabels[d])) || !isFinite(xLabels[d]))) {
          return xLabels[d];
        } else {
          return d;
        }
      });

      $scope.chart[type].yAxis.tickFormat(function(d) {
        if (yLabels[d] && (isNaN(parseFloat(yLabels[d])) || !isFinite(yLabels[d]))) {
          return yLabels[d];
        } else {
          return d;
        }
      });

      // configure how the tooltip looks.
      $scope.chart[type].tooltipContent(function(key, x, y, data) {
        var tooltipContent = '<h3>' + key + '</h3>';
        if ($scope.paragraph.config.graph.scatter.size &&
            $scope.isValidSizeOption($scope.paragraph.config.graph.scatter, $scope.paragraph.result.rows)) {
              tooltipContent += '<p>' + data.point.size + '</p>';
        }

        return tooltipContent;
      });

      $scope.chart[type].showDistX(true)
                        .showDistY(true)
                        //handle the problem of tooltip not showing when muliple points have same value.
                        .scatter.useVoronoi(false);
    } else {
      var p = pivot(data);
      if (type === 'pieChart') {
        var d = pivotDataToD3ChartFormat(p, true).d3g;

        $scope.chart[type].x(function(d) { return d.label;})
                          .y(function(d) { return d.value;});

        if ( d.length > 0 ) {
          for ( var i=0; i<d[0].values.length ; i++) {
            var e = d[0].values[i];
            d3g.push({
              label : e.x,
              value : e.y
            });
          }
        }
      } else if (type === 'multiBarChart') {
        d3g = pivotDataToD3ChartFormat(p, true, false, type).d3g;
        $scope.chart[type].yAxis.axisLabelDistance(50);
      } else if (type === 'lineChart' || type === 'stackedAreaChart') {
        var pivotdata = pivotDataToD3ChartFormat(p, false, true);
        var xLabels = pivotdata.xLabels;
        d3g = pivotdata.d3g;
        $scope.chart[type].xAxis.tickFormat(function(d) {
          if (xLabels[d] && (isNaN(parseFloat(xLabels[d])) || !isFinite(xLabels[d]))) { // to handle string type xlabel
            return xLabels[d];
          } else {
            return d;
          }
        });
        $scope.chart[type].yAxis.axisLabelDistance(50);
        $scope.chart[type].useInteractiveGuideline(true); // for better UX and performance issue. (https://github.com/novus/nvd3/issues/691)
        $scope.chart[type].forceY([0]); // force y-axis minimum to 0 for line chart.
      }
    }

    var renderChart = function() {
      if (!refresh) {
        // TODO force destroy previous chart
      }

      var height = $scope.paragraph.config.graph.height;

      var animationDuration = 300;
      var numberOfDataThreshold = 150;
      // turn off animation when dataset is too large. (for performance issue)
      // still, since dataset is large, the chart content sequentially appears like animated.
      try {
        if (d3g[0].values.length > numberOfDataThreshold) {
          animationDuration = 0;
        }
      } catch(ignoreErr) {
      }

      var chartEl = d3.select('#p'+$scope.paragraph.id+'_'+type+' svg')
          .attr('height', $scope.paragraph.config.graph.height)
          .style('height', height + 'px')
          .datum(d3g)
          .transition()
          .duration(animationDuration)
          .call($scope.chart[type]);
      nv.utils.windowResize($scope.chart[type].update);
    };

    var retryRenderer = function() {
      if ($('#p'+$scope.paragraph.id+'_'+type+' svg').length !== 0) {
        try {
          renderChart();
        } catch(err) {
          console.log('Chart drawing error %o', err);
        }
      } else {
        $timeout(retryRenderer,10);
      }
    };
    $timeout(retryRenderer);
  };

  $scope.isGraphMode = function(graphName) {
    if (($scope.getResultType() === 'TABLE' || $scope.getResultType() === 'CHART') && $scope.getGraphMode()===graphName) {
      return true;
    } else {
      return false;
    }
  };

  $scope.$on('onDragStart_datasourceFromSidebar', function(event, dragDatasource) {
  	$scope.dragDatasource = dragDatasource;
  });
  
  $scope.$on('onDragging_datasourceFromSidebar', function(event, position) {
  	var cursor = $scope.editor.renderer.screenToTextCoordinates(position.pageX, position.pageY);
  	$scope.editor.moveCursorToPosition(cursor)
  });
    
  $scope.$on('onDragStart_columnFromSidebar', function(event, columns) {
  	$scope.dragColumns = columns;
  });
  
  $scope.$on('onDragging_columnFromSidebar', function(event, position) {
  	var cursor = $scope.editor.renderer.screenToTextCoordinates(position.pageX, position.pageY);
  	$scope.editor.moveCursorToPosition(cursor)
  });
    
  $scope.onDroppedDatasoruceColumns = function(event, ui) {
  	console.info('$scope.dragDatasource ', $scope.dragDatasource, '$scope.dragColumns', $scope.dragColumns );
  	var dropText = '';
  	if($scope.dragDatasource !== undefined) {
  		dropText = " source:\"" + $scope.dragDatasource.datsrcName + "\" ";
  		$scope.dragDatasource = undefined;
    	
  	} else if($scope.dragColumns !== undefined) {
  		dropText = ' ' + $scope.dragColumns.toString() + ' ';
  		$scope.dragColumns = undefined;
  	}
  	var position = $scope.editor.getCursorPosition();
  	$scope.editor.getSession().insert(position, dropText);
  	$scope.$parent.toggleSidebar();
  };
  
  $scope.onGraphOptionChange = function() {
    clearUnknownColsFromGraphOption();
    $scope.setGraphMode($scope.paragraph.config.graph.mode, true, false);
  };

  $scope.removeGraphOptionKeys = function(idx) {
    $scope.paragraph.config.graph.keys.splice(idx, 1);
    clearUnknownColsFromGraphOption();
    $scope.setGraphMode($scope.paragraph.config.graph.mode, true, false);
  };

  $scope.removeGraphOptionValues = function(idx) {
    $scope.paragraph.config.graph.values.splice(idx, 1);
    clearUnknownColsFromGraphOption();
    $scope.setGraphMode($scope.paragraph.config.graph.mode, true, false);
  };

  $scope.removeGraphOptionGroups = function(idx) {
    $scope.paragraph.config.graph.groups.splice(idx, 1);
    clearUnknownColsFromGraphOption();
    $scope.setGraphMode($scope.paragraph.config.graph.mode, true, false);
  };

  $scope.setGraphOptionValueAggr = function(idx, aggr) {
    $scope.paragraph.config.graph.values[idx].aggr = aggr;
    clearUnknownColsFromGraphOption();
    $scope.setGraphMode($scope.paragraph.config.graph.mode, true, false);
  };

  $scope.removeScatterOptionXaxis = function(idx) {
    $scope.paragraph.config.graph.scatter.xAxis = null;
    clearUnknownColsFromGraphOption();
    $scope.setGraphMode($scope.paragraph.config.graph.mode, true, false);
  };

  $scope.removeScatterOptionYaxis = function(idx) {
    $scope.paragraph.config.graph.scatter.yAxis = null;
    clearUnknownColsFromGraphOption();
    $scope.setGraphMode($scope.paragraph.config.graph.mode, true, false);
  };

  $scope.removeScatterOptionGroup = function(idx) {
    $scope.paragraph.config.graph.scatter.group = null;
    clearUnknownColsFromGraphOption();
    $scope.setGraphMode($scope.paragraph.config.graph.mode, true, false);
  };

  $scope.removeScatterOptionSize = function(idx) {
    $scope.paragraph.config.graph.scatter.size = null;
    clearUnknownColsFromGraphOption();
    $scope.setGraphMode($scope.paragraph.config.graph.mode, true, false);
  };

  /* Clear unknown columns from graph option */
  var clearUnknownColsFromGraphOption = function() {
    var unique = function(list) {
      for (var i = 0; i<list.length; i++) {
        for (var j=i+1; j<list.length; j++) {
          if (angular.equals(list[i], list[j])) {
            list.splice(j, 1);
          }
        }
      }
    };

    var removeUnknown = function(list) {
      for (var i = 0; i<list.length; i++) {
        // remove non existing column
        var found = false;
        for (var j=0; j<$scope.paragraph.result.columnNames.length; j++) {
          var a = list[i];
          var b = $scope.paragraph.result.columnNames[j];
          if (a.index === b.index && a.name === b.name) {
            found = true;
            break;
          }
        }
        if (!found) {
          list.splice(i, 1);
        }
      }
    };

    var removeUnknownFromScatterSetting = function(fields) {
      for (var f in fields) {
        if (fields[f]) {
          var found = false;
          for (var i = 0; i < $scope.paragraph.result.columnNames.length; i++) {
            var a = fields[f];
            var b = $scope.paragraph.result.columnNames[i];
            if (a.index === b.index && a.name === b.name) {
              found = true;
              break;
            }
          }
          if (!found) {
            fields[f] = null;
          }
        }
      }
    };

    unique($scope.paragraph.config.graph.keys);
    removeUnknown($scope.paragraph.config.graph.keys);

    removeUnknown($scope.paragraph.config.graph.values);

    unique($scope.paragraph.config.graph.groups);
    removeUnknown($scope.paragraph.config.graph.groups);

    removeUnknownFromScatterSetting($scope.paragraph.config.graph.scatter);
  };

  /* select default key and value if there're none selected */
  var selectDefaultColsForGraphOption = function() {
    if ($scope.paragraph.config.graph.keys.length === 0 && $scope.paragraph.result.columnNames.length > 0) {
      $scope.paragraph.config.graph.keys.push($scope.paragraph.result.columnNames[0]);
    }

    if ($scope.paragraph.config.graph.values.length === 0 && $scope.paragraph.result.columnNames.length > 1) {
      $scope.paragraph.config.graph.values.push($scope.paragraph.result.columnNames[1]);
    }

    if (!$scope.paragraph.config.graph.scatter.xAxis && !$scope.paragraph.config.graph.scatter.yAxis) {
      if ($scope.paragraph.result.columnNames.length > 1) {
        $scope.paragraph.config.graph.scatter.xAxis = $scope.paragraph.result.columnNames[0];
        $scope.paragraph.config.graph.scatter.yAxis = $scope.paragraph.result.columnNames[1];
      } else if ($scope.paragraph.result.columnNames.length === 1) {
        $scope.paragraph.config.graph.scatter.xAxis = $scope.paragraph.result.columnNames[0];
      }
    }
  };
  
  var getColumnName = function(name) {
    for(var i in $scope.paragraph.result.columnNames) {
      if(name == $scope.paragraph.result.columnNames[i].name) {
        return $scope.paragraph.result.columnNames[i];
      }
    }
  } 
  
  var selectDefaultColsForGraphOptionByIndex = function() {
    $scope.paragraph.config.graph.keys = [];
    $scope.paragraph.config.graph.groups = [];
    $scope.paragraph.config.graph.values = [];
    if (!UtilService.isOrNullUndefinedEmpty($scope.paragraph.result.options.key)) {
      $scope.paragraph.config.graph.keys.push(getColumnName($scope.paragraph.result.options.key));
    }
    if (!UtilService.isOrNullUndefinedEmpty($scope.paragraph.result.options.group)) {
      $scope.paragraph.config.graph.groups.push(getColumnName($scope.paragraph.result.options.group));
    }
    if (!UtilService.isOrNullUndefinedEmpty($scope.paragraph.result.options.value)) {
      var value = $scope.paragraph.result.options.value;
      if(value instanceof Object) {
        for(var name in value) {
          var obj = value[name];
          if(obj instanceof Array) {
            for(var i=0 ; i<obj.length ; i++) {
              $scope.paragraph.config.graph.values.push(getColumnName(obj[i]));  
            }
          } else if(typeof obj === "string" || obj instanceof String) {
            console.info('obj String',obj);
            $scope.paragraph.config.graph.values.push(getColumnName(obj));  
          }
        }
      } else {
        $scope.paragraph.config.graph.values.push(getColumnName($scope.paragraph.result.options.value));  
      }
      if (!UtilService.isOrNullUndefinedEmpty($scope.paragraph.result.options.min)) {
        $scope.paragraph.config.graph.values.push(getColumnName($scope.paragraph.result.options.min));
      }
      if (!UtilService.isOrNullUndefinedEmpty($scope.paragraph.result.options.max)) {
        $scope.paragraph.config.graph.values.push(getColumnName($scope.paragraph.result.options.max));
      }
      if (!UtilService.isOrNullUndefinedEmpty($scope.paragraph.result.options.expected)) {
        $scope.paragraph.config.graph.values.push(getColumnName($scope.paragraph.result.options.expected));
      }      
      if (!UtilService.isOrNullUndefinedEmpty($scope.paragraph.result.options.latitude)) {
        $scope.paragraph.config.graph.values.push(getColumnName($scope.paragraph.result.options.latitude));
      }
      if (!UtilService.isOrNullUndefinedEmpty($scope.paragraph.result.options.longitude)) {
        $scope.paragraph.config.graph.values.push(getColumnName($scope.paragraph.result.options.longitude));
      }
      if (!UtilService.isOrNullUndefinedEmpty($scope.paragraph.result.options.size)) {
        $scope.paragraph.config.graph.values.push(getColumnName($scope.paragraph.result.options.size));
      }
      if (!UtilService.isOrNullUndefinedEmpty($scope.paragraph.result.options.z)) {
        $scope.paragraph.config.graph.values.push(getColumnName($scope.paragraph.result.options.z));
      }
    }
  };

  var pivot = function(data, isWithoutAggr) {
    var keys = $scope.paragraph.config.graph.keys;
    var groups = $scope.paragraph.config.graph.groups;
    var values = $scope.paragraph.config.graph.values;

    var aggrFunc = {
      sum : function(a,b) {
        var varA = (a !== undefined) ? (isNaN(a) ? 1 : parseFloat(a)) : 0;
        var varB = (b !== undefined) ? (isNaN(b) ? 1 : parseFloat(b)) : 0;
        return varA+varB;
      },
      count : function(a,b) {
        var varA = (a !== undefined) ? parseInt(a) : 0;
        var varB = (b !== undefined) ? 1 : 0;
        return varA+varB;
      },
      min : function(a,b) {
        var varA = (a !== undefined) ? (isNaN(a) ? 1 : parseFloat(a)) : 0;
        var varB = (b !== undefined) ? (isNaN(b) ? 1 : parseFloat(b)) : 0;
        return Math.min(varA,varB);
      },
      max : function(a,b) {
        var varA = (a !== undefined) ? (isNaN(a) ? 1 : parseFloat(a)) : 0;
        var varB = (b !== undefined) ? (isNaN(b) ? 1 : parseFloat(b)) : 0;
        return Math.max(varA,varB);
      },
      avg : function(a,b,c) {
        var varA = (a !== undefined) ? (isNaN(a) ? 1 : parseFloat(a)) : 0;
        var varB = (b !== undefined) ? (isNaN(b) ? 1 : parseFloat(b)) : 0;
        return varA+varB;
      }
    };

    var aggrFuncDiv = {
      sum : false,
      count : false,
      min : false,
      max : false,
      avg : true
    };

    var schema = {};
    var rows = {};

    for (var i=0; i < data.rows.length; i++) {
      var row = data.rows[i];
      var newRow = {};
      var s = schema;
      var p = rows;

      for (var k=0; k < keys.length; k++) {
        var key = keys[k];

        // add key to schema
        if (!s[key.name]) {
          s[key.name] = {
            order : k,
            index : key.index,
            type : 'key',
            children : {}
          };
        }
        s = s[key.name].children;

        // add key to row
        var keyKey = row[key.index];
        if (!p[keyKey]) {
          p[keyKey] = {};
        }
        p = p[keyKey];
      }

      for (var g=0; g < groups.length; g++) {
        var group = groups[g];
        var groupKey = row[group.index];

        // add group to schema
        if (!s[groupKey]) {
          s[groupKey] = {
             order : g,
             index : group.index,
             type : 'group',
             children : {}
          };
        }
        s = s[groupKey].children;

        // add key to row
        if (!p[groupKey]) {
          p[groupKey] = {};
        }
        p = p[groupKey];
      }

      for (var v=0; v < values.length; v++) {
        var value = values[v];
        var valueKey = value.name + (isWithoutAggr == true ? '' : '('+value.aggr+')');

        // add value to schema
        if (!s[valueKey]) {
          s[valueKey] = {
            type : 'value',
            order : v,
            index : value.index
          };
        }

        // add value to row
        if (!p[valueKey]) {
          p[valueKey] = {
              value : (value.aggr !== 'count') ? row[value.index] : 1,
              count: 1
          };
        } else {
          p[valueKey] = {
              value : aggrFunc[value.aggr](p[valueKey].value, row[value.index], p[valueKey].count+1),
              count : (aggrFuncDiv[value.aggr]) ?  p[valueKey].count+1 : p[valueKey].count
          };
        }
      }
    }

    //console.log("schema=%o, rows=%o", schema, rows);

    return {
      schema : schema,
      rows : rows
    };
  };

  var pivotDataToD3ChartFormat = function(data, allowTextXAxis, fillMissingValues, chartType) {
    // construct d3 data
    var d3g = [];

    var schema = data.schema;
    var rows = data.rows;
    var values = $scope.paragraph.config.graph.values;

    var concat = function(o, n) {
      if (!o) {
        return n;
      } else {
        return o+'.'+n;
      }
    };

    var getSchemaUnderKey = function(key, s) {
      for (var c in key.children) {
        s[c] = {};
        getSchemaUnderKey(key.children[c], s[c]);
      }
    };

    var traverse = function(sKey, s, rKey, r, func, rowName, rowValue, colName) {
      //console.log("TRAVERSE sKey=%o, s=%o, rKey=%o, r=%o, rowName=%o, rowValue=%o, colName=%o", sKey, s, rKey, r, rowName, rowValue, colName);

      if (s.type==='key') {
        rowName = concat(rowName, sKey);
        rowValue = concat(rowValue, rKey);
      } else if (s.type==='group') {
        colName = concat(colName, rKey);
      } else if (s.type==='value' && sKey===rKey || valueOnly) {
        colName = concat(colName, rKey);
        func(rowName, rowValue, colName, r);
      }

      for (var c in s.children) {
        if (fillMissingValues && s.children[c].type === 'group' && r[c] === undefined) {
          var cs = {};
          getSchemaUnderKey(s.children[c], cs);
          traverse(c, s.children[c], c, cs, func, rowName, rowValue, colName);
          continue;
        }

        for (var j in r) {
          if (s.children[c].type === 'key' || c === j) {
            traverse(c, s.children[c], j, r[j], func, rowName, rowValue, colName);
          }
        }
      }
    };

    var keys = $scope.paragraph.config.graph.keys;
    var groups = $scope.paragraph.config.graph.groups;
    var values = $scope.paragraph.config.graph.values;
    var valueOnly = (keys.length === 0 && groups.length === 0 && values.length > 0);
    var noKey = (keys.length === 0);
    var isMultiBarChart = (chartType === 'multiBarChart');

    var sKey = Object.keys(schema)[0];

    var rowNameIndex = {};
    var rowIdx = 0;
    var colNameIndex = {};
    var colIdx = 0;
    var rowIndexValue = {};

    for (var k in rows) {
      traverse(sKey, schema[sKey], k, rows[k], function(rowName, rowValue, colName, value) {
        //console.log("RowName=%o, row=%o, col=%o, value=%o", rowName, rowValue, colName, value);
        if (rowNameIndex[rowValue] === undefined) {
          rowIndexValue[rowIdx] = rowValue;
          rowNameIndex[rowValue] = rowIdx++;
        }

        if (colNameIndex[colName] === undefined) {
          colNameIndex[colName] = colIdx++;
        }
        var i = colNameIndex[colName];
        if (noKey && isMultiBarChart) {
          i = 0;
        }

        if (!d3g[i]) {
          d3g[i] = {
            values : [],
            key : (noKey && isMultiBarChart) ? 'values' : colName
          };
        }

        var xVar = isNaN(rowValue) ? ((allowTextXAxis) ? rowValue : rowNameIndex[rowValue]) : parseFloat(rowValue);
        var yVar = 0;
        if (xVar === undefined) { xVar = colName; }
        if (value !== undefined) {
          yVar = isNaN(value.value) ? 0 : parseFloat(value.value) / parseFloat(value.count);
        }
        d3g[i].values.push({
          x : xVar,
          y : yVar
        });
      });
    }

    // clear aggregation name, if possible
    var namesWithoutAggr = {};
    // TODO - This part could use som refactoring - Weird if/else with similar actions and variable names
    for (var colName in colNameIndex) {
      var withoutAggr = colName.substring(0, colName.lastIndexOf('('));
      if (!namesWithoutAggr[withoutAggr]) {
        namesWithoutAggr[withoutAggr] = 1;
      } else {
        namesWithoutAggr[withoutAggr]++;
      }
    }

    if (valueOnly) {
      for (var valueIndex = 0; valueIndex < d3g[0].values.length; valueIndex++) {
        var colName = d3g[0].values[valueIndex].x;
        if (!colName) {
          continue;
        }

        var withoutAggr = colName.substring(0, colName.lastIndexOf('('));
        if (namesWithoutAggr[withoutAggr] <= 1 ) {
          d3g[0].values[valueIndex].x = withoutAggr;
        }
      }
    } else {
      for (var d3gIndex = 0; d3gIndex < d3g.length; d3gIndex++) {
        var colName = d3g[d3gIndex].key;
        var withoutAggr = colName.substring(0, colName.lastIndexOf('('));
        if (namesWithoutAggr[withoutAggr] <= 1 ) {
          d3g[d3gIndex].key = withoutAggr;
        }
      }

      // use group name instead of group.value as a column name, if there're only one group and one value selected.
      if (groups.length === 1 && values.length === 1) {
        for (d3gIndex = 0; d3gIndex < d3g.length; d3gIndex++) {
          var colName = d3g[d3gIndex].key;
          colName = colName.substring(0, colName.lastIndexOf("."));
          d3g[d3gIndex].key = colName;
        }
      }

    }

    return {
      xLabels : rowIndexValue,
      d3g : d3g
    };
  };


  var setDiscreteScatterData = function(data) {
    var xAxis = $scope.paragraph.config.graph.scatter.xAxis;
    var yAxis = $scope.paragraph.config.graph.scatter.yAxis;
    var group = $scope.paragraph.config.graph.scatter.group;

    var xValue;
    var yValue;
    var grp;

    var rows = {};

    for (var i = 0; i < data.rows.length; i++) {
      var row = data.rows[i];
      if (xAxis) {
        xValue = row[xAxis.index];
      }
      if (yAxis) {
        yValue = row[yAxis.index];
      }
      if (group) {
        grp = row[group.index];
      }

      var key = xValue + ',' + yValue +  ',' + grp;

      if(!rows[key]) {
        rows[key] = {
            x : xValue,
            y : yValue,
            group : grp,
            size : 1
        };
      } else {
        rows[key].size++;
      }
    }

    // change object into array
    var newRows = [];
    for(var r in rows){
      var newRow = [];
      if (xAxis) { newRow[xAxis.index] = rows[r].x; }
      if (yAxis) { newRow[yAxis.index] = rows[r].y; }
      if (group) { newRow[group.index] = rows[r].group; }
      newRow[data.rows[0].length] = rows[r].size;
      newRows.push(newRow);
    }
    return newRows;
  };

  var setScatterChart = function(data, refresh) {
    var xAxis = $scope.paragraph.config.graph.scatter.xAxis;
    var yAxis = $scope.paragraph.config.graph.scatter.yAxis;
    var group = $scope.paragraph.config.graph.scatter.group;
    var size = $scope.paragraph.config.graph.scatter.size;

    var xValues = [];
    var yValues = [];
    var rows = {};
    var d3g = [];

    var rowNameIndex = {};
    var colNameIndex = {};
    var grpNameIndex = {};
    var rowIndexValue = {};
    var colIndexValue = {};
    var grpIndexValue = {};
    var rowIdx = 0;
    var colIdx = 0;
    var grpIdx = 0;
    var grpName = '';

    var xValue;
    var yValue;
    var row;

    if (!xAxis && !yAxis) {
      return {
        d3g : []
      };
    }

    for (var i = 0; i < data.rows.length; i++) {
      row = data.rows[i];
      if (xAxis) {
        xValue = row[xAxis.index];
        xValues[i] = xValue;
      }
      if (yAxis) {
        yValue = row[yAxis.index];
        yValues[i] = yValue;
      }
    }

    var isAllDiscrete = ((xAxis && yAxis && isDiscrete(xValues) && isDiscrete(yValues)) ||
                         (!xAxis && isDiscrete(yValues)) ||
                         (!yAxis && isDiscrete(xValues)));

    if (isAllDiscrete) {
      rows = setDiscreteScatterData(data);
    } else {
      rows = data.rows;
    }

    if (!group && isAllDiscrete) {
      grpName = 'count';
    } else if (!group && !size) {
      if (xAxis && yAxis) {
        grpName = '(' + xAxis.name + ', ' + yAxis.name + ')';
      } else if (xAxis && !yAxis) {
        grpName = xAxis.name;
      } else if (!xAxis && yAxis) {
        grpName = yAxis.name;
      }
    } else if (!group && size) {
      grpName = size.name;
    }

    for (i = 0; i < rows.length; i++) {
      row = rows[i];
      if (xAxis) {
        xValue = row[xAxis.index];
      }
      if (yAxis) {
        yValue = row[yAxis.index];
      }
      if (group) {
        grpName = row[group.index];
      }
      var sz = (isAllDiscrete) ? row[row.length-1] : ((size) ? row[size.index] : 1);

      if (grpNameIndex[grpName] === undefined) {
        grpIndexValue[grpIdx] = grpName;
        grpNameIndex[grpName] = grpIdx++;
      }

      if (xAxis && rowNameIndex[xValue] === undefined) {
        rowIndexValue[rowIdx] = xValue;
        rowNameIndex[xValue] = rowIdx++;
      }

      if (yAxis && colNameIndex[yValue] === undefined) {
        colIndexValue[colIdx] = yValue;
        colNameIndex[yValue] = colIdx++;
      }

      if (!d3g[grpNameIndex[grpName]]) {
        d3g[grpNameIndex[grpName]] = {
          key : grpName,
          values : []
        };
      }

      d3g[grpNameIndex[grpName]].values.push({
        x : xAxis ? (isNaN(xValue) ? rowNameIndex[xValue] : parseFloat(xValue)) : 0,
        y : yAxis ? (isNaN(yValue) ? colNameIndex[yValue] : parseFloat(yValue)) : 0,
        size : isNaN(parseFloat(sz))? 1 : parseFloat(sz)
      });
    }

    return {
      xLabels : rowIndexValue,
      yLabels : colIndexValue,
      d3g : d3g
    };
  };

  var isDiscrete = function(field) {
    var getUnique = function(f) {
      var uniqObj = {};
      var uniqArr = [];
      var j = 0;
      for (var i = 0; i < f.length; i++) {
        var item = f[i];
        if(uniqObj[item] !== 1) {
          uniqObj[item] = 1;
          uniqArr[j++] = item;
        }
      }
      return uniqArr;
    };

    for (var i = 0; i < field.length; i++) {
      if(isNaN(parseFloat(field[i])) &&
         (typeof field[i] === 'string' || field[i] instanceof String)) {
        return true;
      }
    }

    var threshold = 0.05;
    var unique = getUnique(field);
    if (unique.length/field.length < threshold) {
      return true;
    } else {
      return false;
    }
  };

  $scope.isValidSizeOption = function (options, rows) {
    var xValues = [];
    var yValues = [];

    for (var i = 0; i < rows.length; i++) {
      var row = rows[i];
      var size = row[options.size.index];

      //check if the field is numeric
      if (isNaN(parseFloat(size)) || !isFinite(size)) {
        return false;
      }

      if (options.xAxis) {
        var x = row[options.xAxis.index];
        xValues[i] = x;
      }
      if (options.yAxis) {
        var y = row[options.yAxis.index];
        yValues[i] = y;
      }
    }

    //check if all existing fields are discrete
    var isAllDiscrete = ((options.xAxis && options.yAxis && isDiscrete(xValues) && isDiscrete(yValues)) ||
        (!options.xAxis && isDiscrete(yValues)) ||
        (!options.yAxis && isDiscrete(xValues)));

    if (isAllDiscrete) {
      return false;
    }

    return true;
  };

  $scope.setGraphHeight = function() {
    var height = $('#p'+$scope.paragraph.id+'_graph').height();

    var newParams = angular.copy($scope.paragraph.settings.params);
    var newConfig = angular.copy($scope.paragraph.config);

    newConfig.graph.height = height;

    commitParagraph($scope.paragraph.title, $scope.paragraph.text, newConfig, newParams);
  };

  $scope.setGraphHeightTable = function() {
    var height = $('#p'+$scope.paragraph.id+'_table').height();

    var newParams = angular.copy($scope.paragraph.settings.params);
    var newConfig = angular.copy($scope.paragraph.config);

    newConfig.graph.heightTable = height;

    commitParagraph($scope.paragraph.title, $scope.paragraph.text, newConfig, newParams);
  };

  /** Utility function */
  if (typeof String.prototype.startsWith !== 'function') {
    String.prototype.startsWith = function(str) {
      return this.slice(0, str.length) === str;
    };
  }

  $scope.goToSingleParagraph = function () {
    var noteId = $route.current.pathParams.noteId;
    var wrkspcId = $route.current.pathParams.wrkspcId;
    var redirectToUrl = location.protocol + '//' + location.host + '/#/notebook/' + noteId + '/paragraph/' + $scope.paragraph.id + '/wrkspcId/' + wrkspcId + '?asIframe';
    $window.open(redirectToUrl);
  };
});
