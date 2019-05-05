package com.mak.eword.mvp.model;

import java.util.List;

/**
 * Created by jayson on 2019/4/16.
 * Content:词霸查询单词实体
 * JSON 字段解释(英文)
 * {
 * 'word_name':'' #单词
 * 'exchange': '' #单词的各种时态
 * 'symbols':'' #单词各种信息 下面字段都是这个字段下面的
 * 'ph_en': '' #英式音标
 * 'ph_am': '' #美式音标
 * 'ph_en_mp3':'' #英式发音
 * 'ph_am_mp3': '' #美式发音
 * 'ph_tts_mp3': '' #TTS发音
 * 'parts':'' #词的各种意思
 * }
 * JSON 字段解释(中文)
 * {
 * 'word_name':'' #所查的词
 * 'symbols':'' #词各种信息 下面字段都是这个字段下面的
 * 'word_symbol': '' #拼音
 * 'symbol_mp3': '' #发音
 * 'parts':'' #汉字的各种翻译 数组
 * 'net_means': '' #网络释义
 * }
 */
public class CibaWordEnBean {

    /**
     * word_name : goo
     * is_CRI : 1
     * exchange : {"word_pl":"","word_third":"","word_past":"","word_done":"","word_ing":"","word_er":"","word_est":""}
     * symbols : [{"ph_en":"gu:","ph_am":"ɡu","ph_other":"","ph_en_mp3":"http://res.iciba.com/resource/amp3/oxford/0/c1/b3/c1b3de10d910673c9a8a16142a240dfd.mp3","ph_am_mp3":"http://res.iciba.com/resource/amp3/1/0/6c/dc/6cdcbae015da6f882373107c90209267.mp3","ph_tts_mp3":"http://res-tts.iciba.com/6/c/d/6cdcbae015da6f882373107c90209267.mp3","parts":[{"part":"n.","means":["黏性物，感伤","臭味"]}]}]
     */

    private String word_name;
    private String is_CRI;
    private List<SymbolsBean> symbols;

    public String getWord_name() {
        return word_name;
    }

    public void setWord_name(String word_name) {
        this.word_name = word_name;
    }

    public String getIs_CRI() {
        return is_CRI;
    }

    public void setIs_CRI(String is_CRI) {
        this.is_CRI = is_CRI;
    }

    public List<SymbolsBean> getSymbols() {
        return symbols;
    }

    public void setSymbols(List<SymbolsBean> symbols) {
        this.symbols = symbols;
    }


    public static class SymbolsBean {
        /**
         * ph_en : gu:
         * ph_am : ɡu
         * ph_other :
         * ph_en_mp3 : http://res.iciba.com/resource/amp3/oxford/0/c1/b3/c1b3de10d910673c9a8a16142a240dfd.mp3
         * ph_am_mp3 : http://res.iciba.com/resource/amp3/1/0/6c/dc/6cdcbae015da6f882373107c90209267.mp3
         * ph_tts_mp3 : http://res-tts.iciba.com/6/c/d/6cdcbae015da6f882373107c90209267.mp3
         * parts : [{"part":"n.","means":["黏性物，感伤","臭味"]}]
         */

        private String ph_en;
        private String ph_am;
        private String ph_other;
        private String ph_en_mp3;
        private String ph_am_mp3;
        private String ph_tts_mp3;
        private List<PartsBean> parts;

        public String getPh_en() {
            return ph_en;
        }

        public void setPh_en(String ph_en) {
            this.ph_en = ph_en;
        }

        public String getPh_am() {
            return ph_am;
        }

        public void setPh_am(String ph_am) {
            this.ph_am = ph_am;
        }

        public String getPh_other() {
            return ph_other;
        }

        public void setPh_other(String ph_other) {
            this.ph_other = ph_other;
        }

        public String getPh_en_mp3() {
            return ph_en_mp3;
        }

        public void setPh_en_mp3(String ph_en_mp3) {
            this.ph_en_mp3 = ph_en_mp3;
        }

        public String getPh_am_mp3() {
            return ph_am_mp3;
        }

        public void setPh_am_mp3(String ph_am_mp3) {
            this.ph_am_mp3 = ph_am_mp3;
        }

        public String getPh_tts_mp3() {
            return ph_tts_mp3;
        }

        public void setPh_tts_mp3(String ph_tts_mp3) {
            this.ph_tts_mp3 = ph_tts_mp3;
        }

        public List<PartsBean> getParts() {
            return parts;
        }

        public void setParts(List<PartsBean> parts) {
            this.parts = parts;
        }

        public static class PartsBean {
            /**
             * part : n.
             * means : ["黏性物，感伤","臭味"]
             */

            private String part;
            private List<String> means;

            public String getPart() {
                return part;
            }

            public void setPart(String part) {
                this.part = part;
            }

            public List<String> getMeans() {
                return means;
            }

            public void setMeans(List<String> means) {
                this.means = means;
            }
        }
    }
}
