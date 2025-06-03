package ge.tbc.itacademy.data;

import ge.tbc.itacademy.mappers.BookingCaseMapper;
import ge.tbc.itacademy.models.BookingCase;
import ge.tbc.itacademy.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.testng.annotations.DataProvider;

import java.util.List;

public class BookingCaseData {
    @DataProvider
    public static Object[][] bookingCasesProvider() {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            BookingCaseMapper mapper = session.getMapper(BookingCaseMapper.class);
            List<BookingCase> bookingCases = mapper.getAllBookingCases();

            Object[][] data = new Object[bookingCases.size()][1];
            for (int i = 0; i < bookingCases.size(); i++) {
                data[i][0] = bookingCases.get(i);
            }
            return data;
        }
    }
}
