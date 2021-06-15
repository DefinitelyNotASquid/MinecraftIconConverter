package sample;

public class YamlMap implements Comparable< YamlMap > {
    public String name;
    public Integer pos;

    public YamlMap(String name, Integer pos){
       this.name = name;
       this.pos = pos;
    }

    public String getName() {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public Integer getPos() {
        return pos;
    }

    public void setPos (Integer pos) {
        this.pos = pos;
    }

    @Override
    public int compareTo(YamlMap o) {
        return this.getPos().compareTo(o.getPos());
    }
}
