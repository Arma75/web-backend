package com.buds.foodNutrient.dao;

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

import com.buds.foodNutrient.dto.FoodNutrientDTO;

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

    private final RowMapper<FoodNutrientDTO> rowMapper = new BeanPropertyRowMapper<>(FoodNutrientDTO.class);
    private final JdbcTemplate jdbcTemplate;

    public FoodNutrientDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int create(FoodNutrientDTO foodNutrientDTO) {
        List<Object> params = new ArrayList<>();
        params.add(foodNutrientDTO.getCompany());
        params.add(foodNutrientDTO.getFood());
        params.add(foodNutrientDTO.getServeSize());
        params.add(foodNutrientDTO.getUnit());
        params.add(foodNutrientDTO.getCalorie());
        params.add(foodNutrientDTO.getCarbohydrate());
        params.add(foodNutrientDTO.getProtein());
        params.add(foodNutrientDTO.getFat());

        return jdbcTemplate.update(INSERT_SQL, params.toArray());
    }

    public int createBulk(List<FoodNutrientDTO> foodNutrientDTOs) {
        int[] createdCounts = jdbcTemplate.batchUpdate(INSERT_SQL, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                FoodNutrientDTO foodNutrientDTO = foodNutrientDTOs.get(i);
                preparedStatement.setString(1, foodNutrientDTO.getCompany());
                preparedStatement.setString(2, foodNutrientDTO.getFood());
                preparedStatement.setObject(3, foodNutrientDTO.getServeSize());
                preparedStatement.setString(4, foodNutrientDTO.getUnit());
                preparedStatement.setObject(5, foodNutrientDTO.getCalorie());
                preparedStatement.setObject(6, foodNutrientDTO.getCarbohydrate());
                preparedStatement.setObject(7, foodNutrientDTO.getProtein());
                preparedStatement.setObject(8, foodNutrientDTO.getFat());
            }

            @Override
            public int getBatchSize() {
                return foodNutrientDTOs.size();
            }
        });

        return Arrays.stream(createdCounts).sum();
    }

    public FoodNutrientDTO findById(Long id) {
        return jdbcTemplate.queryForObject(SELECT_ONE_SQL, rowMapper, id);
    }

    private void appendWhereConditions(StringBuilder sql, List<Object> params, FoodNutrientDTO foodNutrientDTO) {
        if (foodNutrientDTO.getCompany() != null && !foodNutrientDTO.getCompany().isEmpty()) {
            sql.append(" AND COMPANY LIKE '%' || ? || '%'");
            params.add(foodNutrientDTO.getCompany());
        }
        if (foodNutrientDTO.getFood() != null && !foodNutrientDTO.getFood().isEmpty()) {
            sql.append(" AND FOOD LIKE '%' || ? || '%'");
            params.add(foodNutrientDTO.getFood());
        }
        if (foodNutrientDTO.getServeSize() != null) {
            sql.append(" AND SERVE_SIZE = ?");
            params.add(foodNutrientDTO.getServeSize());
        }
        if (foodNutrientDTO.getUnit() != null && !foodNutrientDTO.getUnit().isEmpty()) {
            sql.append(" AND UNIT LIKE '%' || ? || '%'");
            params.add(foodNutrientDTO.getUnit());
        }
        if (foodNutrientDTO.getCalorie() != null) {
            sql.append(" AND CALORIE = ?");
            params.add(foodNutrientDTO.getCalorie());
        }
        if (foodNutrientDTO.getCarbohydrate() != null) {
            sql.append(" AND CARBOHYDRATE = ?");
            params.add(foodNutrientDTO.getCarbohydrate());
        }
        if (foodNutrientDTO.getProtein() != null) {
            sql.append(" AND PROTEIN = ?");
            params.add(foodNutrientDTO.getProtein());
        }
        if (foodNutrientDTO.getFat() != null) {
            sql.append(" AND FAT = ?");
            params.add(foodNutrientDTO.getFat());
        }
        if (foodNutrientDTO.getUseYn() != null && !foodNutrientDTO.getUseYn().isEmpty()) {
            sql.append(" AND USE_YN = ?");
            params.add(foodNutrientDTO.getUseYn());
        }
        if (foodNutrientDTO.getRegDtmStart() != null) {
            sql.append(" AND REG_DTM >= ?");
            params.add(foodNutrientDTO.getRegDtmStart());
        }
        if (foodNutrientDTO.getRegDtmEnd() != null) {
            sql.append(" AND REG_DTM <= ?");
            params.add(foodNutrientDTO.getRegDtmEnd());
        }
        if (foodNutrientDTO.getUpdDtmStart() != null) {
            sql.append(" AND UPD_DTM >= ?");
            params.add(foodNutrientDTO.getUpdDtmStart());
        }
        if (foodNutrientDTO.getUpdDtmEnd() != null) {
            sql.append(" AND UPD_DTM <= ?");
            params.add(foodNutrientDTO.getUpdDtmEnd());
        }
    }

    public List<FoodNutrientDTO> findAll(FoodNutrientDTO foodNutrientDTO) {
        int offset = foodNutrientDTO.getOffset();
        int size = foodNutrientDTO.getSize();
        String sort = foodNutrientDTO.getSort();

        StringBuilder sql = new StringBuilder(SELECT_LIST_SQL);
        List<Object> params = new ArrayList<>();

        appendWhereConditions(sql, params, foodNutrientDTO);

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

    public long countAll(FoodNutrientDTO foodNutrientDTO) {
        StringBuilder sql = new StringBuilder(SELECT_COUNT_SQL);
        List<Object> params = new ArrayList<>();

        this.appendWhereConditions(sql, params, foodNutrientDTO);

        return jdbcTemplate.queryForObject(sql.toString(), Long.class, params.toArray());
    }

    public int update(FoodNutrientDTO foodNutrientDTO) {
        List<Object> params = new ArrayList<>();
        params.add(foodNutrientDTO.getCompany());
        params.add(foodNutrientDTO.getFood());
        params.add(foodNutrientDTO.getServeSize());
        params.add(foodNutrientDTO.getUnit());
        params.add(foodNutrientDTO.getCalorie());
        params.add(foodNutrientDTO.getCarbohydrate());
        params.add(foodNutrientDTO.getProtein());
        params.add(foodNutrientDTO.getFat());
        params.add(foodNutrientDTO.getId());

        return jdbcTemplate.update(UPDATE_SQL, params.toArray());
    }
    public int updateBulk(List<FoodNutrientDTO> foodNutrientDTOs) {
        int[] updatedCounts = jdbcTemplate.batchUpdate(UPDATE_SQL, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                FoodNutrientDTO foodNutrientDTO = foodNutrientDTOs.get(i);
                preparedStatement.setString(1, foodNutrientDTO.getCompany());
                preparedStatement.setString(2, foodNutrientDTO.getFood());
                preparedStatement.setObject(3, foodNutrientDTO.getServeSize());
                preparedStatement.setString(4, foodNutrientDTO.getUnit());
                preparedStatement.setObject(5, foodNutrientDTO.getCalorie());
                preparedStatement.setObject(6, foodNutrientDTO.getCarbohydrate());
                preparedStatement.setObject(7, foodNutrientDTO.getProtein());
                preparedStatement.setObject(8, foodNutrientDTO.getFat());
                preparedStatement.setObject(9, foodNutrientDTO.getId());
            }

            @Override
            public int getBatchSize() {
                return foodNutrientDTOs.size();
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
    public int patch(FoodNutrientDTO foodNutrientDTO) {
        if (foodNutrientDTO.getId() == null) {
            return 0;
        }

        StringBuilder sql = new StringBuilder(PATCH_SQL);
        List<Object> params = new ArrayList<>();

        if (foodNutrientDTO.getCompany() != null) {
            sql.append(", COMPANY = ?");
            params.add(foodNutrientDTO.getCompany());
        }
        if (foodNutrientDTO.getFood() != null) {
            sql.append(", FOOD = ?");
            params.add(foodNutrientDTO.getFood());
        }
        if (foodNutrientDTO.getServeSize() != null) {
            sql.append(", SERVE_SIZE = ?");
            params.add(foodNutrientDTO.getServeSize());
        }
        if (foodNutrientDTO.getUnit() != null) {
            sql.append(", UNIT = ?");
            params.add(foodNutrientDTO.getUnit());
        }
        if (foodNutrientDTO.getCalorie() != null) {
            sql.append(", CALORIE = ?");
            params.add(foodNutrientDTO.getCalorie());
        }
        if (foodNutrientDTO.getCarbohydrate() != null) {
            sql.append(", CARBOHYDRATE = ?");
            params.add(foodNutrientDTO.getCarbohydrate());
        }
        if (foodNutrientDTO.getProtein() != null) {
            sql.append(", PROTEIN = ?");
            params.add(foodNutrientDTO.getProtein());
        }
        if (foodNutrientDTO.getFat() != null) {
            sql.append(", FAT = ?");
            params.add(foodNutrientDTO.getFat());
        }
        if (foodNutrientDTO.getUseYn() != null) {
            sql.append(", USE_YN = ?");
            params.add(foodNutrientDTO.getUseYn());
        }

        sql.append(" WHERE ID = ?");
        params.add(foodNutrientDTO.getId());

        return jdbcTemplate.update(sql.toString(), params.toArray());
    }
    public int patchBulk(List<FoodNutrientDTO> foodNutrientDTOs) {
        int patchedCount = 0;
        for (FoodNutrientDTO foodNutrientDTO : foodNutrientDTOs) {
            patchedCount += this.patch(foodNutrientDTO);
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