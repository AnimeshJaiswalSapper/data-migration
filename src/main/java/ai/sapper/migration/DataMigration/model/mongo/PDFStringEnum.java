package ai.sapper.migration.DataMigration.model.mongo;

import java.io.Serializable;
import java.util.List;

public class PDFStringEnum implements Serializable {

    private static final long serialVersionUID = 655216102245821349L;

    String enumString;
    boolean isBullet, isRoot;
    List<Integer> group;

    public PDFStringEnum(String enumeration, boolean isBullet, boolean isRoot) {
        this.enumString = enumeration;
        this.isBullet = isBullet;
        this.isRoot = isRoot;
    }

    public PDFStringEnum(String enumeration, boolean isBullet, boolean isRoot,List<Integer> group) {
        this.enumString = enumeration;
        this.isBullet = isBullet;
        this.isRoot = isRoot;
        this.group=group;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }
    public  String getEnumString() {
        return enumString;
    }


    public boolean isBullet() {
        return isBullet;
    }

    public boolean isRoot() {
        return isRoot;
    }
    public List<Integer> getGroup() {
        return group;
    }

    public void setGroup(List<Integer> group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return enumString+"=Grp="+group;
    }
}

