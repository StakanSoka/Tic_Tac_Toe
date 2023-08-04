package dao;

public class LayoutPatternDAO extends AbstractDAO<LayoutPatternDAO, Integer> {

    @Override
    public Class<LayoutPatternDAO> getEntityClass() {
        return LayoutPatternDAO.class;
    }

    @Override
    public String getTableName() {
        return "layout_pattern";
    }
}
