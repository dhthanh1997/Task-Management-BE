package com.ansv.taskmanagement.mapper;

import com.ansv.taskmanagement.constants.StateEnum;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.DefaultMapperFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
public class BaseMapper<Model, DTO> {
    private static final MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

    private MapperFacade mapperFacade;
    private Class<Model> model;
    private Class<DTO> dto;

    public BaseMapper(Class<Model> model, Class<DTO> dto) {
        mapperFactory.classMap(model, dto).constructorA().constructorB().byDefault().register();
        mapperFacade = mapperFactory.getMapperFacade();
        this.dto = dto;
        this.model = model;
    }

    public BaseMapper(Class<Model> model, Class<DTO> dto, Map<String, String> customFields) {
        mapperFactory.classMap(model, dto).constructorA().constructorB().byDefault().customize(
                new CustomMapper<Model, DTO>() {
                    @Override
                    public void mapAtoB(Model model, DTO dto, MappingContext context) {
                        try {
                            context = new MappingContext.Factory().getContext();
                            for(Map.Entry<String, String> item: customFields.entrySet()) {
                                context.setProperty(item.getKey(), item.getValue());
                            }
                            super.mapAtoB(model, dto, context);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).register();

        mapperFacade = mapperFactory.getMapperFacade();
        this.dto = dto;
        this.model = model;
    }

    public BaseMapper() {
        mapperFacade = mapperFactory.getMapperFacade();
    }

    public DTO toDtoBean(Model _model) {
        if (_model == null) {
            return null;
        }
        return mapperFacade.map(_model, dto);
    }

    public Model toPersistenceBean(DTO _dto) {
        if (_dto == null) {
            return null;
        }
        return mapperFacade.map(_dto, model);
    }

    public List<DTO> toDtoBean(Iterable<Model> _models) {
        List<DTO> dtoBeans = new ArrayList<>();
        if (_models == null) {
            return dtoBeans;
        }
        for (Model model : _models) {
            dtoBeans.add(toDtoBean(model));
        }
        return dtoBeans;
    }

    public List<Model> toPersistenceBean(Iterable<DTO> listDTO) {
        List<Model> models = new ArrayList<>();
        if (listDTO == null) {
            return models;
        }
        for (DTO dto : listDTO) {
            models.add(toPersistenceBean(dto));
        }
        return models;
    }
}
