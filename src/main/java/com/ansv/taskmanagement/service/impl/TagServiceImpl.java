package com.ansv.taskmanagement.service.impl;

import com.ansv.taskmanagement.dto.criteria.SearchCriteria;
import com.ansv.taskmanagement.dto.response.TagDTO;
import com.ansv.taskmanagement.dto.specification.GenericSpecificationBuilder;
import com.ansv.taskmanagement.mapper.BaseMapper;
import com.ansv.taskmanagement.model.Tag;
import com.ansv.taskmanagement.model.Task;
import com.ansv.taskmanagement.repository.TagRepository;
import com.ansv.taskmanagement.service.TagService;
import com.ansv.taskmanagement.util.DataUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class TagServiceImpl implements TagService {

    private static final Logger logger = LoggerFactory.getLogger(TagServiceImpl.class);

    private static final BaseMapper<Tag, TagDTO> mapper = new BaseMapper<>(Tag.class, TagDTO.class);

    @Autowired
    private TagRepository repository;

    @Override
    public TagDTO findById(Long id) {
        if (DataUtils.notNull(id)) {
            Optional<Tag> entity = repository.findById(id);
            if (entity.isPresent()) {
                return mapper.toDtoBean(entity.get());
            }
        }
        return null;
    }

    @Override
    public TagDTO save(TagDTO item) {
//        try {
        Tag entity = null;

        TagDTO dto = findById(item.getId());
        if (DataUtils.notNull(dto)) {
            entity.setLastModifiedDate(LocalDateTime.now());
        }
        entity = mapper.toPersistenceBean(item);
        return mapper.toDtoBean(repository.save(entity));
//
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//            return null;
//        }
    }

    @Override
    public List<TagDTO> findAll() {
        List<Tag> entities = repository.findAll();
        return mapper.toDtoBean(entities);
    }

    @Override
    public List<TagDTO> search(Map<String, Object> mapParam) {
        return null;
    }

    @Override
    public Page<TagDTO> findBySearchCriteria(Optional<String> search, Pageable page) {
//        try {
        GenericSpecificationBuilder<Tag> builder = new GenericSpecificationBuilder<>();

        // check chuỗi để tách các param search
        if (DataUtils.notNull(search)) {
            Pattern pattern = Pattern.compile("(\\w+?)(\\.)(:|<|>|(\\w+?))(\\.)(\\w+?),", Pattern.UNICODE_CHARACTER_CLASS);
            Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                builder.with(new SearchCriteria(matcher.group(1), matcher.group(3), matcher.group(6)));
            }
        }
        // specification
        builder.setClazz(Tag.class);
        Specification<Tag> spec = builder.build();
        Page<TagDTO> listDTO = repository.findAll(spec, page).map(entity -> {
            TagDTO dto = mapper.toDtoBean(entity);
            return dto;
        });
        return listDTO;
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//            return null;
//        }
    }

    @Override
    public void deleteById(Long id) {
//        try {
        repository.deleteById(id);
//            return 1;
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//            return -1;
//        }
    }

    @Override
    public Integer deleteByListId(List<Long> listId) {
//        try {
        return repository.deleteByListId(listId);
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//            return -1;
//        }
    }
}
