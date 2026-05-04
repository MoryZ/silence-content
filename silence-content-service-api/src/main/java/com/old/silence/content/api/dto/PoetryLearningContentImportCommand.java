package com.old.silence.content.api.dto;

import java.util.List;

/**
 * PoetryLearningContent命令对象
 */
public class PoetryLearningContentImportCommand {

    /**
     * 序号
     */
    private Integer sequence;

    /**
     * 例词（虚词）
     */
    private String exampleWord;

    /**
     * 释义列表
     */
    private List<Definition> definitions;


    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public String getExampleWord() {
        return exampleWord;
    }

    public void setExampleWord(String exampleWord) {
        this.exampleWord = exampleWord;
    }

    public List<Definition> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(List<Definition> definitions) {
        this.definitions = definitions;
    }


    /**
     * 释义实体类（一个虚词的某个义项）
     */
    public static class Definition {
        /**
         * 义项编号
         */
        private Integer index;

        /**
         * 词义解释
         */
        private String meaning;

        /**
         * 例句列表
         */
        private List<Example> examples;

        /**
         * 单个例句时的兼容字段（当只有一句例句时使用）
         * 注意：根据你的JSON，有的义项用的是"examples"数组，有的是"example"单对象
         */
        private String example;

        private String source;


        public Integer getIndex() {
            return index;
        }

        public void setIndex(Integer index) {
            this.index = index;
        }

        public String getMeaning() {
            return meaning;
        }

        public void setMeaning(String meaning) {
            this.meaning = meaning;
        }

        public List<Example> getExamples() {
            return examples;
        }

        public void setExamples(List<Example> examples) {
            this.examples = examples;
        }

        public String getExample() {
            return example;
        }

        public void setExample(String example) {
            this.example = example;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        /**
         * 例句实体类
         */
        public static class Example {
            /**
             * 例句内容
             */
            private String sentence;

            /**
             * 出处（教材名称及年级）
             */
            private String source;

            // 构造方法
            public Example() {
            }

            public Example(String sentence, String source) {
                this.sentence = sentence;
                this.source = source;
            }

            // Getter & Setter
            public String getSentence() {
                return sentence;
            }

            public void setSentence(String sentence) {
                this.sentence = sentence;
            }

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
            }


        }
    }
}