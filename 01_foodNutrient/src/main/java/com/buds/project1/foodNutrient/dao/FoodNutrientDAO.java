package com.buds.project1.foodNutrient.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.buds.project1.foodNutrient.dto.FoodNutrientRequest;
import com.buds.project1.foodNutrient.dto.FoodNutrientResponse;

@Repository
public class FoodNutrientDAO {
    private final static String INSERT_SQL = "INSERT INTO FOOD_NUTRIENT (COMPANY, FOOD, SERVE_SIZE, UNIT, CALORIE, CARBOHYDRATE, PROTEIN, FAT) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private final static String SELECT_ONE_SQL = "SELECT ID, COMPANY, FOOD, SERVE_SIZE, UNIT, CALORIE, CARBOHYDRATE, PROTEIN, FAT, USE_YN, REG_DTM, UPD_DTM FROM FOOD_NUTRIENT WHERE ID = ?";
    private final static String SELECT_LIST_SQL = "SELECT ID, COMPANY, FOOD, SERVE_SIZE, UNIT, CALORIE, CARBOHYDRATE, PROTEIN, FAT, USE_YN, REG_DTM, UPD_DTM FROM FOOD_NUTRIENT WHERE 1=1";
    private final static String SELECT_COUNT_SQL = "SELECT COUNT(*) FROM FOOD_NUTRIENT WHERE 1=1";
    private final static String UPDATE_SQL = "UPDATE FOOD_NUTRIENT SET COMPANY = ?, FOOD = ?, SERVE_SIZE = ?, UNIT = ?, CALORIE = ?, CARBOHYDRATE = ?, PROTEIN = ?, FAT = ?, UPD_DTM = CURRENT_TIMESTAMP WHERE ID = ?";
    private final static String PATCH_SQL = "UPDATE FOOD_NUTRIENT SET UPD_DTM = CURRENT_TIMESTAMP";
    private final static String UNUSE_SQL = "UPDATE FOOD_NUTRIENT SET USE_YN = 'N', UPD_DTM = CURRENT_TIMESTAMP WHERE ID = ?";
    private final static String DELETE_SQL = "DELETE FROM FOOD_NUTRIENT WHERE ID = ?";

    private final RowMapper<FoodNutrientResponse> rowMapper = new BeanPropertyRowMapper<>(FoodNutrientResponse.class);
    private final JdbcTemplate jdbcTemplate;

    public FoodNutrientDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int create(FoodNutrientRequest foodNutrientRequest) {
        List<Object> params = new ArrayList<>();
        params.add(foodNutrientRequest.getCompany());
        params.add(foodNutrientRequest.getFood());
        params.add(foodNutrientRequest.getServeSize());
        params.add(foodNutrientRequest.getUnit());
        params.add(foodNutrientRequest.getCalorie());
        params.add(foodNutrientRequest.getCarbohydrate());
        params.add(foodNutrientRequest.getProtein());
        params.add(foodNutrientRequest.getFat());

        return jdbcTemplate.update(INSERT_SQL, params.toArray());
    }

    public int createBulk(List<FoodNutrientRequest> foodNutrientRequests) {
        int[] createdCounts = jdbcTemplate.batchUpdate(INSERT_SQL, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                FoodNutrientRequest foodNutrientRequest = foodNutrientRequests.get(i);
                preparedStatement.setString(1, foodNutrientRequest.getCompany());
                preparedStatement.setString(2, foodNutrientRequest.getFood());
                preparedStatement.setObject(3, foodNutrientRequest.getServeSize());
                preparedStatement.setString(4, foodNutrientRequest.getUnit());
                preparedStatement.setObject(5, foodNutrientRequest.getCalorie());
                preparedStatement.setObject(6, foodNutrientRequest.getCarbohydrate());
                preparedStatement.setObject(7, foodNutrientRequest.getProtein());
                preparedStatement.setObject(8, foodNutrientRequest.getFat());
            }

            @Override
            public int getBatchSize() {
                return foodNutrientRequests.size();
            }
        });

        return Arrays.stream(createdCounts).sum();
    }

    public FoodNutrientResponse findById(Long id) {
        return jdbcTemplate.queryForObject(SELECT_ONE_SQL, rowMapper, id);
    }

    private void appendWhereConditions(StringBuilder sql, List<Object> params, FoodNutrientRequest foodNutrientRequest) {
        if (foodNutrientRequest.getCompany() != null && !foodNutrientRequest.getCompany().isEmpty()) {
            sql.append(" AND COMPANY LIKE '%' || ? || '%'");
            params.add(foodNutrientRequest.getCompany());
        }
        if (foodNutrientRequest.getFood() != null && !foodNutrientRequest.getFood().isEmpty()) {
            sql.append(" AND FOOD LIKE '%' || ? || '%'");
            params.add(foodNutrientRequest.getFood());
        }
        if (foodNutrientRequest.getServeSize() != null) {
            sql.append(" AND SERVE_SIZE = ?");
            params.add(foodNutrientRequest.getServeSize());
        }
        if (foodNutrientRequest.getUnit() != null && !foodNutrientRequest.getUnit().isEmpty()) {
            sql.append(" AND UNIT LIKE '%' || ? || '%'");
            params.add(foodNutrientRequest.getUnit());
        }
        if (foodNutrientRequest.getCalorie() != null) {
            sql.append(" AND CALORIE = ?");
            params.add(foodNutrientRequest.getCalorie());
        }
        if (foodNutrientRequest.getCarbohydrate() != null) {
            sql.append(" AND CARBOHYDRATE = ?");
            params.add(foodNutrientRequest.getCarbohydrate());
        }
        if (foodNutrientRequest.getProtein() != null) {
            sql.append(" AND PROTEIN = ?");
            params.add(foodNutrientRequest.getProtein());
        }
        if (foodNutrientRequest.getFat() != null) {
            sql.append(" AND FAT = ?");
            params.add(foodNutrientRequest.getFat());
        }
        if (foodNutrientRequest.getUseYn() != null && !foodNutrientRequest.getUseYn().isEmpty()) {
            sql.append(" AND USE_YN = ?");
            params.add(foodNutrientRequest.getUseYn());
        }
        if (foodNutrientRequest.getRegDtmStart() != null) {
            sql.append(" AND REG_DTM >= ?");
            params.add(foodNutrientRequest.getRegDtmStart());
        }
        if (foodNutrientRequest.getRegDtmEnd() != null) {
            sql.append(" AND REG_DTM <= ?");
            params.add(foodNutrientRequest.getRegDtmEnd());
        }
        if (foodNutrientRequest.getUpdDtmStart() != null) {
            sql.append(" AND UPD_DTM >= ?");
            params.add(foodNutrientRequest.getUpdDtmStart());
        }
        if (foodNutrientRequest.getUpdDtmEnd() != null) {
            sql.append(" AND UPD_DTM <= ?");
            params.add(foodNutrientRequest.getUpdDtmEnd());
        }
    }

    public List<FoodNutrientResponse> findAll(FoodNutrientRequest foodNutrientRequest) {
        int offset = foodNutrientRequest.getOffset();
        int size = foodNutrientRequest.getSize();
        String sort = foodNutrientRequest.getSort();

        StringBuilder sql = new StringBuilder(SELECT_LIST_SQL);
        List<Object> params = new ArrayList<>();

        appendWhereConditions(sql, params, foodNutrientRequest);

        if (sort != null && !sort.isBlank()) {
            sql.append(" ORDER BY");
            String[] orders = sort.split(";");
            for (int i = 0; i < orders.length; i++) {
                String[] tokens = orders[i].split(",");
                if (tokens.length < 2) {
                    continue;
                }
                String column = tokens[0];
                String direction = tokens[1];

                List<String> allowedColumns = List.of("ID", "COMPANY", "SERVE_SIZE", "UNIT", "FOOD", "CALORIE", "CARBOHYDRATE", "PROTEIN", "FAT", "USE_YN", "REG_DTM", "UPD_DTM");
                if (!allowedColumns.contains(column.toUpperCase())) {
                    throw new IllegalArgumentException("Invalid sort parameter.");
                }
                if (!"ASC".equalsIgnoreCase(direction) && !"DESC".equalsIgnoreCase(direction)) {
                    throw new IllegalArgumentException("Invalid sort parameter.");
                }

                if (i > 0) {
                    sql.append(",");
                }
                sql.append(" " + column + " " + direction);
            }
        } else {
            sql.append(" ORDER BY ID DESC");
        }

        sql.append(" LIMIT ? OFFSET ?");
        params.add(size);
        params.add(offset);

        return jdbcTemplate.query(sql.toString(), rowMapper, params.toArray());
    }

    public long countAll(FoodNutrientRequest foodNutrientRequest) {
        StringBuilder sql = new StringBuilder(SELECT_COUNT_SQL);
        List<Object> params = new ArrayList<>();

        this.appendWhereConditions(sql, params, foodNutrientRequest);

        return jdbcTemplate.queryForObject(sql.toString(), Long.class, params.toArray());
    }

    public int update(FoodNutrientRequest foodNutrientRequest) {
        List<Object> params = new ArrayList<>();
        params.add(foodNutrientRequest.getCompany());
        params.add(foodNutrientRequest.getFood());
        params.add(foodNutrientRequest.getServeSize());
        params.add(foodNutrientRequest.getUnit());
        params.add(foodNutrientRequest.getCalorie());
        params.add(foodNutrientRequest.getCarbohydrate());
        params.add(foodNutrientRequest.getProtein());
        params.add(foodNutrientRequest.getFat());
        params.add(foodNutrientRequest.getId());

        return jdbcTemplate.update(UPDATE_SQL, params.toArray());
    }
    public int updateBulk(List<FoodNutrientRequest> foodNutrientRequests) {
        int[] updatedCounts = jdbcTemplate.batchUpdate(UPDATE_SQL, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                FoodNutrientRequest foodNutrientRequest = foodNutrientRequests.get(i);
                preparedStatement.setString(1, foodNutrientRequest.getCompany());
                preparedStatement.setString(2, foodNutrientRequest.getFood());
                preparedStatement.setObject(3, foodNutrientRequest.getServeSize());
                preparedStatement.setString(4, foodNutrientRequest.getUnit());
                preparedStatement.setObject(5, foodNutrientRequest.getCalorie());
                preparedStatement.setObject(6, foodNutrientRequest.getCarbohydrate());
                preparedStatement.setObject(7, foodNutrientRequest.getProtein());
                preparedStatement.setObject(8, foodNutrientRequest.getFat());
                preparedStatement.setObject(9, foodNutrientRequest.getId());
            }

            @Override
            public int getBatchSize() {
                return foodNutrientRequests.size();
            }
        });

        return Arrays.stream(updatedCounts).sum();
    }

    public int unuse(Long id) {
        return jdbcTemplate.update(UNUSE_SQL, id);
    }
    public int unuseBulk(List<Long> ids) {
        int[] updatedCounts = jdbcTemplate.batchUpdate(UNUSE_SQL, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                Long id = ids.get(i);
                preparedStatement.setObject(1, id);
            }

            @Override
            public int getBatchSize() {
                return ids.size();
            }
        });

        return Arrays.stream(updatedCounts).sum();
    }
    public int patch(FoodNutrientRequest foodNutrientRequest) {
        if (foodNutrientRequest.getId() == null) {
            return 0;
        }

        StringBuilder sql = new StringBuilder(PATCH_SQL);
        List<Object> params = new ArrayList<>();

        if (foodNutrientRequest.getCompany() != null) {
            sql.append(", COMPANY = ?");
            params.add(foodNutrientRequest.getCompany());
        }
        if (foodNutrientRequest.getFood() != null) {
            sql.append(", FOOD = ?");
            params.add(foodNutrientRequest.getFood());
        }
        if (foodNutrientRequest.getServeSize() != null) {
            sql.append(", SERVE_SIZE = ?");
            params.add(foodNutrientRequest.getServeSize());
        }
        if (foodNutrientRequest.getUnit() != null) {
            sql.append(", UNIT = ?");
            params.add(foodNutrientRequest.getUnit());
        }
        if (foodNutrientRequest.getCalorie() != null) {
            sql.append(", CALORIE = ?");
            params.add(foodNutrientRequest.getCalorie());
        }
        if (foodNutrientRequest.getCarbohydrate() != null) {
            sql.append(", CARBOHYDRATE = ?");
            params.add(foodNutrientRequest.getCarbohydrate());
        }
        if (foodNutrientRequest.getProtein() != null) {
            sql.append(", PROTEIN = ?");
            params.add(foodNutrientRequest.getProtein());
        }
        if (foodNutrientRequest.getFat() != null) {
            sql.append(", FAT = ?");
            params.add(foodNutrientRequest.getFat());
        }
        if (foodNutrientRequest.getUseYn() != null) {
            sql.append(", USE_YN = ?");
            params.add(foodNutrientRequest.getUseYn());
        }

        sql.append(" WHERE ID = ?");
        params.add(foodNutrientRequest.getId());

        return jdbcTemplate.update(sql.toString(), params.toArray());
    }
    public int patchBulk(List<FoodNutrientRequest> foodNutrientRequests) {
        int patchedCount = 0;
        for (FoodNutrientRequest foodNutrientRequest : foodNutrientRequests) {
            patchedCount += this.patch(foodNutrientRequest);
        }

        return patchedCount;
    }

    public int delete(Long id) {
        return jdbcTemplate.update(DELETE_SQL, id);
    }
    public int deleteBulk(List<Long> ids) {
        int[] deletedCounts = jdbcTemplate.batchUpdate(DELETE_SQL, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                Long id = ids.get(i);
                preparedStatement.setObject(1, id);
            }

            @Override
            public int getBatchSize() {
                return ids.size();
            }
        });

        return Arrays.stream(deletedCounts).sum();
    }
}