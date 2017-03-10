package po;

import util.FieldCount;

import java.util.EnumSet;

/**
 * Created by SilverNarcissus on 2017/3/3.
 */
public class MoviePO {
    /**
     * 电影序列号
     */
    private String id;

    /**
     * 电影名称
     */
    private String name;

    public MoviePO() {

    }

    public MoviePO(String id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * 提供一个编辑PO的编辑器
     *
     * @return 编辑器
     */
    public static Builder getBuilder() {
        return new Builder();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static class Builder {
        //要build的产品
        private MoviePO product;
        //用于查看域是否填充完全
        private EnumSet<FieldCount> fieldCount;

        private Builder() {
            product = new MoviePO();
            fieldCount = EnumSet.noneOf(FieldCount.class);
        }

        public Builder setId(String id) {
            product.id = id;
            fieldCount.add(FieldCount.attribute1);
            return this;
        }


        public Builder setName(String name) {
            product.name = name;
            fieldCount.add(FieldCount.attribute2);
            return this;
        }

        public MoviePO build() {
            if (!valid()) {
                throw new IllegalStateException("MoviePO's fields aren't complete!");
            }
            return product;
        }

        //检查产品的重要属性是否均设置完全
        private boolean valid() {
            return fieldCount.size() == 2;
        }
    }
}
