package sn.sastrans.backofficev2.security.repositoriesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import sn.sastrans.backofficev2.security.models.Role;
import sn.sastrans.backofficev2.security.models.User;
import sn.sastrans.backofficev2.security.repositories.RoleRepositoryCustom;
import sn.sastrans.backofficev2.trace.models.Evenement;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.Set;

public class RoleRepositoryImpl implements RoleRepositoryCustom {
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Role> getOtherRolesUser(Integer userId) {

        // Créer un objet CriteriaBuilder pour construire la requête
        CriteriaBuilder cbuild = entityManager.getCriteriaBuilder();

// Créer un objet CriteriaQuery pour spécifier le type de résultat
        CriteriaQuery<Role> cquery = cbuild.createQuery(Role.class);

// Définir la racine de la requête en utilisant la classe Role
        Root<Role> roleRoot = cquery.from(Role.class);

        Subquery<Role> subquery = cquery.subquery(Role.class);
        Root<User> userRoot = subquery.from(User.class);
        subquery.select(userRoot.join("roles")).where(cbuild.equal(userRoot.get("id"), userId));

        cquery.select(roleRoot).where(cbuild.not(roleRoot.in(subquery)));


// Exécuter la requête
        List<Role> roles = entityManager.createQuery(cquery).getResultList();
        return roles;
    }
}
