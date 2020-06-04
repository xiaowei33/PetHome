package cn.itsource.org.service.impl;

import cn.itsource.basic.config.Mail;
import cn.itsource.basic.util.PageList;
import cn.itsource.org.domain.Employee;
import cn.itsource.org.domain.Shop;
import cn.itsource.org.mapper.ActivationMapper;
import cn.itsource.org.mapper.EmpMapper;
import cn.itsource.org.mapper.ShopMapper;
import cn.itsource.org.query.DepartmentQuery;
import cn.itsource.org.service.IActivationService;
import cn.itsource.org.service.IShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivationServiceImpl implements IActivationService {
    @Autowired
    private ActivationMapper activationMapper;
    @Autowired
    private EmpMapper empMapper;
   @Autowired
    private Mail mail;

    @Override
    public void update(Shop shop) {
        activationMapper.update(shop);
        empMapper.updatename(shop.getEmployee());
        System.out.println(shop.getEmployee().getUsername());
        Employee employee = empMapper.selectByName(shop.getEmployee());
        String email = employee.getEmail();
        try {
            mail.Activation(email);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(Long id) {
        activationMapper.delete(id);
    }

    @Override
    public List<Shop> selectAll() {
        return activationMapper.selectAll();
    }

    @Override
    public PageList<Shop> paging(DepartmentQuery query) {
        Long total = activationMapper.total(query);
        if (total==null){
           return new PageList<>();
        }
        List<Shop> data = activationMapper.paging(query);
        return new PageList<>(total,data);
    }

}
