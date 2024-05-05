package com.javaweb.repository.custom.impl;

import java.sql.Connection;
import java.lang.reflect.Field;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

//import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.entity.BuildingEntity;
import com.javaweb.model.request.BuildingSearchRequest;
import com.javaweb.model.response.BuildingSearchResponse;
import com.javaweb.repository.custom.BuildingRepositoryCustom;
import org.apache.logging.log4j.message.ExitMessage;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;



@Repository
@Primary
public class BuildingRepositoryImpl implements BuildingRepositoryCustom {
	@PersistenceContext
	private EntityManager entityManager;

	public static void joinTable(BuildingSearchRequest buildingSearchRequest, StringBuilder sql) {
		Long staffId = buildingSearchRequest.getStaffId();
		if (staffId != null) {
			sql.append(" INNER JOIN assignmentbuilding ON b.id = assignmentbuilding.buildingid ");
			sql.append(" INNER JOIN user ON assignmentbuilding.staffid = user.id ");
		}
	}
	public static void queryNormal(BuildingSearchRequest buildingSearchRequest, StringBuilder where) {
		try {
			Field[] fields = BuildingSearchRequest.class.getDeclaredFields();
			for (Field item : fields) {
				item.setAccessible(true);
				String fieldName = item.getName();
				if (!fieldName.equals("staffId") && !fieldName.equals("typeCode") && !fieldName.startsWith("area")
						&& !fieldName.startsWith("rentPrice")) {
					Object value = item.get(buildingSearchRequest);
					if (value != null && !value.toString().equalsIgnoreCase("")) {
						if (item.getType().getName().equals("java.lang.Long")
								|| item.getType().getName().equals("java.lang.Integer")
								|| item.getType().getName().equals("java.lang.Float")) {
							where.append(" AND b." + fieldName + " = " + value);
						} else if (item.getType().getName().equals("java.lang.String")) {
							where.append(" AND b." + fieldName + " LIKE '%" + value + "%' ");
						}
					}
				}

			}

		} catch (Exception ex) {
			ex.printStackTrace();

		}
	}

	public static void querySpecial(BuildingSearchRequest buildingSearchRequest, StringBuilder where) {
		Long staffId = buildingSearchRequest.getStaffId();
		if (staffId != null) {
			where.append(" AND assignmentbuilding.staffid = " + staffId);
		}
		Long areaTo = buildingSearchRequest.getAreaTo();
		Long areaFrom = buildingSearchRequest.getAreaFrom();
		if (areaTo != null || areaFrom != null) {
			where.append(" AND EXISTS (SELECT * FROM rentarea r WHERE b.id = r.buildingid ");
			if (areaFrom != null) {
				where.append(" AND r.value >= " + areaFrom);
			}
			if (areaTo != null) {
				where.append(" AND r.value <= " + areaTo);
			}
			where.append(") ");
		}
		Long rentPriceTo = buildingSearchRequest.getRentPriceTo();
		Long rentPriceFrom = buildingSearchRequest.getRentPriceFrom();
		if (rentPriceFrom != null || rentPriceTo != null) {
			if (rentPriceFrom != null) {
				where.append(" AND b.rentprice >= " + rentPriceFrom);
			}
			if (rentPriceTo != null) {
				where.append(" AND b.rentprice <= " + rentPriceTo);
			}
		}
		List<String> typeCode = buildingSearchRequest.getTypeCode();
		if (typeCode != null && typeCode.size() != 0) {
			where.append(" AND ( ");
			String sql = typeCode.stream().map(it -> " b.type Like " + "'%" + it + "%' ")
					.collect(Collectors.joining(" OR "));
			where.append(sql);
			where.append(" ) ");
		}
	}

	@Override
	public List<BuildingEntity> findAll(BuildingSearchRequest buildingSearchRequest) {
		StringBuilder sql = new StringBuilder("SELECT b.* FROM building b ");
		joinTable(buildingSearchRequest, sql);
		StringBuilder where = new StringBuilder(" WHERE 1=1 ");
		queryNormal(buildingSearchRequest, where);
		querySpecial(buildingSearchRequest, where);
		where.append(" GROUP BY b.id ");
		sql.append(where);
		Query query = entityManager.createNativeQuery(sql.toString(), BuildingEntity.class);
		return query.getResultList();
	}

	private String buildQueryFilter() {
		String sql = "SELECT * FROM building b ";
		return sql;
	}

	public List<BuildingEntity> getAllBuildings(Pageable pageable) {

		StringBuilder sql = new StringBuilder(buildQueryFilter())
				.append(" LIMIT ").append(pageable.getPageSize()).append("\n")
				.append(" OFFSET ").append(pageable.getOffset());
		System.out.println("Final query: " + sql.toString());

		Query query = entityManager.createNativeQuery(sql.toString(), BuildingEntity.class);
		return query.getResultList();
	}
	public int countTotalItem() {
		String sql = buildQueryFilter();
		Query query = entityManager.createNativeQuery(sql.toString());
		return query.getResultList().size();
	}

}
