package web.dao;

import jakarta.transaction.Transactional;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import web.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
@EnableTransactionManagement
@Transactional
public class UserDaoImpl implements UserDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public UserDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<User> getListUserLimit(int limit) {

        List<User> users=new ArrayList<>();
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/postgres"
                            , "postgres", "postgres");
            Statement statement= connection.createStatement();
            statement.executeQuery("SELECT * from usersTask2_3_1");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public User getUserById(int id) {
        String queryHQL = "from User where id=?1";
        Query q = sessionFactory.getCurrentSession().createQuery(queryHQL, User.class);
        q.setParameter(1, id);
        return (User) q.getResultList().stream().findFirst().orElse(new User());
    }

    @Override
    public void update(User user, int id) {
        User userToBeUpDated = getUserById(id);
        userToBeUpDated.setName(user.getName());
        userToBeUpDated.setSurname(user.getSurname());
        userToBeUpDated.setAge(user.getAge());
        saveUser(userToBeUpDated);
    }

    @Override
    public void deleteUser(User user) {
        sessionFactory.getCurrentSession().remove(user);
    }

    @Override
    public List<User> getListUser() {
        String queryHQL = "from User";
        List<User> users = sessionFactory.getCurrentSession()
                .createQuery(queryHQL, User.class)
                .getResultList();
        return users;
    }

    @Override
    public void saveUser(User user) {
        sessionFactory.getCurrentSession().persist(user);
    }
}