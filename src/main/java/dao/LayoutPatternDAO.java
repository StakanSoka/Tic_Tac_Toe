package dao;

import bean.LayoutPattern;
import org.springframework.stereotype.Component;

@Component
public class LayoutPatternDAO extends AbstractDAO<LayoutPattern, Integer> {

    @Override
    public Class<LayoutPattern> getEntityClass() {
        return LayoutPattern.class;
    }

    @Override
    public String getTableName() {
        return "layout_pattern";
    }
}
