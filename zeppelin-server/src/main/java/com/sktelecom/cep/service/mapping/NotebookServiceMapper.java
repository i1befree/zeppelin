package com.sktelecom.cep.service.mapping;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.sktelecom.cep.entity.Notebook;
import com.sktelecom.cep.vo.NotebookVo;

/**
 * Mapping between entity beans and display beans.
 */
@Component
public class NotebookServiceMapper extends AbstractServiceMapper {

  /**
   * Constructor.
   */
  public NotebookServiceMapper() {
    modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    
    PropertyMap<Notebook, NotebookVo> notebookMap = new PropertyMap<Notebook, NotebookVo>() {
      @Override
      protected void configure() {
        skip().setCreator(null);
        skip().setOwner(null);
        skip().setWorkspaceAssigns(null);
      }
    };
    modelMapper.addMappings(notebookMap);
  }

}

