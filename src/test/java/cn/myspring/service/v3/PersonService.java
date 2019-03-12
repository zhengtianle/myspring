package cn.myspring.service.v3;

import cn.myspring.dao.NameDao;
import cn.myspring.dao.SexDao;


/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-12
 * @Author ZhengTianle
 * Description:
 */
public class PersonService {
    private NameDao nameDao;

    private SexDao sexDao;

    private int age;

    public PersonService(NameDao nameDao, SexDao sexDao) {
        this.nameDao = nameDao;
        this.sexDao = sexDao;
        this.age = 0;
    }

    public PersonService(NameDao nameDao, SexDao sexDao, int age) {
        this.nameDao = nameDao;
        this.sexDao = sexDao;
        this.age = age;
    }

    public NameDao getNameDao() {
        return nameDao;
    }

    public SexDao getSexDao() {
        return sexDao;
    }

    public int getAge() {
        return age;
    }
}
