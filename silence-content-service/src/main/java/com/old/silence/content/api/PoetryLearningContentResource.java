package com.old.silence.content.api;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.assembler.PoetryLearningContentMapper;
import com.old.silence.content.api.dto.PoetryLearningContentCommand;
import com.old.silence.content.api.dto.PoetryLearningContentImportCommand;
import com.old.silence.content.api.dto.PoetryLearningContentQuery;
import com.old.silence.content.domain.model.poetry.PoetryLearningContent;
import com.old.silence.content.domain.repository.poetry.PoetryLearningContentRepository;
import com.old.silence.core.util.CollectionUtils;
import com.old.silence.data.jdbc.repository.query.QueryCriteriaConverter;
import com.old.silence.json.JacksonMapper;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static com.old.silence.webmvc.util.RestControllerUtils.validateModifyingResult;

/**
 * PoetryLearningContent资源控制器
 */
@RestController
@RequestMapping("/api/v1")
public class PoetryLearningContentResource implements PoetryLearningContentService {
    private final PoetryLearningContentRepository poetryLearningContentRepository;
    private final PoetryLearningContentMapper poetryLearningContentMapper;

    public PoetryLearningContentResource(PoetryLearningContentRepository poetryLearningContentRepository,
                                         PoetryLearningContentMapper poetryLearningContentMapper) {
        this.poetryLearningContentRepository = poetryLearningContentRepository;
        this.poetryLearningContentMapper = poetryLearningContentMapper;
    }

    @Override
    public <T> Optional<T> findById(BigInteger id, Class<T> projectionType) {
        return poetryLearningContentRepository.findById(id, projectionType);
    }

    @Override
    public <T> List<T> findByIds(List<BigInteger> ids, Class<T> projectionType) {
        return poetryLearningContentRepository.findByIds(ids, projectionType);
    }

    @Override
    public long countByCriteria(PoetryLearningContentQuery query) {
        var criteria = QueryCriteriaConverter.convert(query, PoetryLearningContent.class);
        return poetryLearningContentRepository.countByCriteria(criteria);
    }

    @Override
    public <T> Page<T> query(PoetryLearningContentQuery query, Pageable pageable, Class<T> projectionType) {
        var criteria = QueryCriteriaConverter.convert(query, PoetryLearningContent.class);
        return poetryLearningContentRepository.findByCriteria(criteria, pageable, projectionType);
    }

    @Override
    public BigInteger create(PoetryLearningContentCommand command) {
        var poetryLearningContent = poetryLearningContentMapper.convert(command);
        poetryLearningContentRepository.create(poetryLearningContent);
        return poetryLearningContent.getId();
    }

    @Override
    public int bulkCreate(List<PoetryLearningContentCommand> poetryLearningCommands) {
        var poetryLearningContents = CollectionUtils.transformToList(poetryLearningCommands, poetryLearningContentMapper::convert);
        return poetryLearningContentRepository.bulkCreate(poetryLearningContents);
    }

    @Override
    public void update(BigInteger id, PoetryLearningContentCommand command) {
        var poetryLearningContent = poetryLearningContentMapper.convert(command);
        poetryLearningContent.setId(id);
        validateModifyingResult(poetryLearningContentRepository.update(poetryLearningContent));
    }

    @Override
    public void deleteById(BigInteger id) {
        validateModifyingResult(poetryLearningContentRepository.deleteById(id));
    }


    @PostMapping("/poetryLearningContents/inner/{gradeId}/{categoryId}/{subCategoryId}")
    public void importFromJson(@RequestBody @Validated List<PoetryLearningContentImportCommand> commands,
                              @PathVariable BigInteger gradeId, @PathVariable BigInteger categoryId, @PathVariable BigInteger subCategoryId) {
        for (var command : commands) {
            var poetryLearningContent = new PoetryLearningContent();
            poetryLearningContent.setTitle(command.getExampleWord() + "字解析" );
            poetryLearningContent.setSubtitle(command.getExampleWord() + "字学习");
            poetryLearningContent.setContentType(2L);
            poetryLearningContent.setGradeId(gradeId);
            poetryLearningContent.setCategoryId(categoryId);
            poetryLearningContent.setSubCategoryId(subCategoryId);
            poetryLearningContent.setDifficulty(RandomUtils.insecure().randomLong(1, 3));
            var originalText = buildOriginalText(command.getDefinitions());
            poetryLearningContent.setOriginalText(command.getExampleWord() + "在文言文中含义:" + originalText);
            poetryLearningContent.setBackground("文言文" + (BigInteger.valueOf(6).compareTo(subCategoryId) == 0 ? "虚词" : "实词") + "学习");
            poetryLearningContent.setExplanation(command.getExampleWord() + "可以表示:" + buildExplanation(command.getDefinitions()) + "等");
            poetryLearningContent.setUsageExamples(buildUsageExamples(command.getDefinitions()));
            poetryLearningContent.setAnnotations(JacksonMapper.getSharedInstance().toJson(CollectionUtils.transformToList(command.getDefinitions(),
                    PoetryLearningContentImportCommand.Definition::getMeaning)));
            poetryLearningContent.setTranslation(command.getExampleWord() + "的含义:" + originalText);
            poetryLearningContent.setAppreciation(command.getExampleWord() + "字体现了古代人民的智慧");
            poetryLearningContent.setEnabled(true);
            poetryLearningContent.setDisplayOrder(command.getSequence());
            poetryLearningContentRepository.create(poetryLearningContent);

        }

    }

    private String buildUsageExamples(List<PoetryLearningContentImportCommand.Definition> definitions) {
        StringBuilder sb = new StringBuilder();
        for (var definition : definitions) {
            sb.append(definition.getIndex()).append("、").append("\n");

            if (CollectionUtils.isNotEmpty(definition.getExamples())) {
                for (var example : definition.getExamples()) {
                    sb.append(example.getSentence()).append(" ").append(example.getSource()) ;
                }

            } else {
                sb.append(definition.getExample()).append(" ").append(definition.getSource());
            }

        }
        return sb.toString();
    }

    private String buildOriginalText(List<PoetryLearningContentImportCommand.Definition> definitions) {
        StringBuilder sb = new StringBuilder();
        for (var definition : definitions) {
            sb.append(definition.getIndex()).append("、").append(definition.getMeaning());
        }
        return sb.toString();
    }

    private String buildExplanation(List<PoetryLearningContentImportCommand.Definition> definitions) {
        StringBuilder sb = new StringBuilder();
        for (var definition : definitions) {
            sb.append("、").append(definition.getMeaning());
        }
        return sb.toString();
    }
}