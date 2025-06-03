package ge.tbc.itacademy.data;

import ge.tbc.itacademy.mappers.BookingCaseMapper;
import ge.tbc.itacademy.models.BookingCase;
import ge.tbc.itacademy.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class DatabaseSteps {
    public List<BookingCase> getAllBookingCases() {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            BookingCaseMapper mapper = session.getMapper(BookingCaseMapper.class);
            return mapper.getAllBookingCases();
        }
    }
}
