package ru.otus.l9.jsonwriter;

public class Demo {

    public static void main(String... arg) {
        BagOfPrimitives obj = new BagOfPrimitives( 22, "test", 10 );
        System.out.println( obj );
        MyJson myson = new MyJson();
        String json = myson.forJsonObject( obj ).toJSONString();
        System.out.println( json );

        BagOfPrimitives obj2 = myson.fromJson( json, BagOfPrimitives.class );
        System.out.println( obj.equals( obj2 ) );
        System.out.println( obj2 );
    }

    static class BagOfPrimitives {
        private final int value1;
        private final String value2;
        private final int value3;

        public BagOfPrimitives(Integer value1, String value2, Integer value3) {
            this.value1 = value1;
            this.value2 = value2;
            this.value3 = value3;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            BagOfPrimitives that = (BagOfPrimitives) o;

            if (value1 != that.value1) return false;
            if (value3 != that.value3) return false;
            return value2 != null ? value2.equals( that.value2 ) : that.value2 == null;
        }

        @Override
        public String toString() {
            return "BagOfPrimitives{" +
                    "value1=" + value1 +
                    ", value2='" + value2 + '\'' +
                    ", value3=" + value3 +
                    '}';
        }
    }

}
