package cn.itsource.org.service.impl;

import cn.itsource.basic.config.Mail;
import cn.itsource.basic.util.AjaxResult;
import cn.itsource.basic.util.FastDfsUtil;
import cn.itsource.basic.util.PageList;
import cn.itsource.org.domain.Shop;
import cn.itsource.org.mapper.EmpMapper;
import cn.itsource.org.mapper.ShopMapper;
import cn.itsource.org.query.DepartmentQuery;
import cn.itsource.org.service.IShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.List;
@Service
public class ShopServiceImpl implements IShopService {
    @Autowired
    private ShopMapper shopMapper;
    @Autowired
    private EmpMapper empMapper;
   @Autowired
    private Mail mail;
    @Override
    public void save(Shop shop) {
        shopMapper.save(shop);
    }

    @Override
    public void update(Shop shop) {
        shopMapper.update(shop);
    }

    @Override
    public void remove(Long id) {
        //先删除存储的照片
        //通过id查询出该条数据
        Shop shop = shopMapper.selectById(id);
        //获取logo的值
        String logo = shop.getLogo();
        //分割字符串
        String[] split = logo.split("/");
        String logo1=split[2]+"/"+split[3]+"/"+split[4]+"/"+split[5];
        //调用方法，删除照片
        FastDfsUtil.delete(split[1],logo1);
        //删除数据库上的数据
        shopMapper.delete(id);

    }

    @Override
    public List<Shop> selectAll() {
        return shopMapper.selectAll();
    }

    @Override
    public PageList<Shop> paging(DepartmentQuery query) {
        Long total = shopMapper.total(query);
        if (total==null){
           return new PageList<>();
        }
        List<Shop> data = shopMapper.paging(query);
        return new PageList<>(total,data);
    }

    @Override
    public void settledIn(Shop shop) {
        //设置state的值，初始化是等待启用 1
        shop.getEmployee().setState(1);

        empMapper.save(shop.getEmployee());
        //发送邮件给管理员告知有一个账户待激活
        try {
            mail.sigh();
        }catch (Exception e) {
            e.printStackTrace();
        }
        shopMapper.save(shop);


    }
}
