package cn.myspring.service.v2;

import cn.myspring.dao.NameDao;
import cn.myspring.dao.SexDao;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-10
 * @Author ZhengTianle
 * Description:
 * 测试seeter注入的service类
 */
public class PersonService {
    private NameDao nameDao;

    private SexDao sexDao;

    private int age;

    private String school;

    public NameDao getNameDao() {
        return nameDao;
    }

    public void setNameDao(NameDao nameDao) {
        this.nameDao = nameDao;
    }

    public SexDao getSexDao() {
        return sexDao;
    }

    public void setSexDao(SexDao sexDao) {
        this.sexDao = sexDao;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }
}
