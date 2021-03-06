package cn.myspring.service.v4;

import cn.myspring.beans.annotation.Autowired;
import cn.myspring.dao.v4.NameDao;
import cn.myspring.dao.v4.SexDao;
import cn.myspring.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-3-13
 * @Author ZhengTianle
 * Description:
 */
@Component(value="person")
public class PersonService {

    @Autowired
    private NameDao nameDao;

    @Autowired
    private SexDao sexDao;

    public NameDao getNameDao() {
        return nameDao;
    }

    public SexDao getSexDao() {
        return sexDao;
    }
}
