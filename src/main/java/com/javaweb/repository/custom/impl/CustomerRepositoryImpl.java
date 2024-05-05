package com.javaweb.repository.custom.impl;

import com.javaweb.entity.BuildingEntity;
import com.javaweb.entity.CustomerEntity;
import com.javaweb.model.request.BuildingSearchRequest;
import com.javaweb.model.request.CustomerSearchRequest;
import com.javaweb.repository.custom.CustomerRepositoryCustom;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.lang.reflect.Field;
import java.util.List;

@Repository
public class CustomerRepositoryImpl implements CustomerRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    public static void joinTable(CustomerSearchRequest customerSearchRequest, StringBuilder sql){
        Long staffId = customerSearchRequest.getStaffId();
        if(staffId != null){
            sql.append(" JOIN assignmentcustomer ass ON c.id = ass.customerid ");
        }
    }

    public static void checkQuery(CustomerSearchRequest customerSearchRequest, StringBuilder sql) {
        try {
            Field[] fields = CustomerSearchRequest.class.getDeclaredFields();
            for (Field item : fields) {
                item.setAccessible(true);
                String fieldName = item.getName();
                if (!fieldName.equals("staffId") ){
                    Object value = item.get(customerSearchRequest);
                    if (value != null && !value.toString().equalsIgnoreCase("")) {
                        sql.append(" AND c." + fieldName + " LIKE '%" + value + "%' ");
                    }
                }

            }

        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }

    public static void checkStaff(CustomerSearchRequest customerSearchRequest, StringBuilder where) {
        Long staffId = customerSearchRequest.getStaffId();
        if (staffId != null) {
            where.append(" AND ass.staffid = " + staffId);
        }
    }
    @Override
    public List<CustomerEntity> findAll(CustomerSearchRequest customerSearchRequest) {
        StringBuilder sql = new StringBuilder("SELECT c.* FROM customer c ");
        joinTable(customerSearchRequest, sql);
        sql.append(" WHERE 1=1 AND c.is_active = 1");
        checkQuery(customerSearchRequest, sql);
        checkStaff(customerSearchRequest, sql);
        sql.append(" GROUP BY c.id ");
        Query query = entityManager.createNativeQuery(sql.toString(), CustomerEntity.class);
        return query.getResultList();
    }

    private String customerQueryFilter() {
        String sql = "SELECT * FROM customer c ";
        return sql;
    }

    public List<CustomerEntity> getAllCustomers(Pageable pageable) {

        StringBuilder sql = new StringBuilder(customerQueryFilter())
                .append(" LIMIT ").append(pageable.getPageSize()).append("\n")
                .append(" OFFSET ").append(pageable.getOffset());
        System.out.println("Final query: " + sql.toString());

        Query query = entityManager.createNativeQuery(sql.toString(), CustomerEntity.class);
        return query.getResultList();
    }
    public int countTotalItem() {
        String sql = customerQueryFilter();
        Query query = entityManager.createNativeQuery(sql.toString());
        return query.getResultList().size();
    }
}
