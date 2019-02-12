package ru.eta.appliancecontrol.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ru.eta.appliancecontrol.repository.OvenRepository;

import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureJsonTesters
public class OvenTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OvenRepository ovenRepository;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private JacksonTester<Oven> json;

    @Test
    @Sql(value = "/data.sql")
    public void checkMappingWithDB() throws Exception {
        Oven ovenFromFile = json.readObject("/oven.json");
        Oven ovenFromDB = ovenRepository.getOne(ovenFromFile.getId());

        assertThat(this.json.write(ovenFromDB)).isEqualTo("/oven.json");
    }

    @Test
    public void checkEqualsMethod() throws Exception {
        Oven oven = json.readObject("/oven.json");
        Oven ovenCopy = json.readObject("/oven.json");

        assertTrue(oven != ovenCopy);
        assertTrue(oven.equals(ovenCopy));
    }
}

