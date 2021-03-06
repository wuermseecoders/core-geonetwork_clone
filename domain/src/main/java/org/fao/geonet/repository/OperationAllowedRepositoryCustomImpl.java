/*
 * Copyright (C) 2001-2016 Food and Agriculture Organization of the
 * United Nations (FAO-UN), United Nations World Food Programme (WFP)
 * and United Nations Environment Programme (UNEP)
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at
 * your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301, USA
 *
 * Contact: Jeroen Ticheler - FAO - Viale delle Terme di Caracalla 2,
 * Rome - Italy. email: geonetwork@osgeo.org
 */

package org.fao.geonet.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import org.fao.geonet.domain.Metadata;
import org.fao.geonet.domain.MetadataSourceInfo_;
import org.fao.geonet.domain.Metadata_;
import org.fao.geonet.domain.OperationAllowed;
import org.fao.geonet.domain.OperationAllowedId;
import org.fao.geonet.domain.OperationAllowedId_;
import org.fao.geonet.domain.OperationAllowed_;
import org.springframework.data.jpa.domain.Specification;

import com.google.common.base.Optional;

/**
 * Implementation for all {@link OperationAllowed} queries that cannot be automatically generated by
 * Spring-data.
 *
 * @author Jesse
 */
public class OperationAllowedRepositoryCustomImpl implements OperationAllowedRepositoryCustom {

    @PersistenceContext
    EntityManager _entityManager;

    @Override
    public List<OperationAllowed> findByMetadataId(String metadataId) {
        CriteriaBuilder builder = _entityManager.getCriteriaBuilder();
        CriteriaQuery<OperationAllowed> query = builder.createQuery(OperationAllowed.class);

        int iMdId = Integer.parseInt(metadataId);
        Root<OperationAllowed> root = query.from(OperationAllowed.class);

        ParameterExpression<Integer> idParameter = builder.parameter(Integer.class, "id");
        query.where(builder.equal(idParameter, root.get(OperationAllowed_.id).get(OperationAllowedId_.metadataId)));
        return _entityManager.createQuery(query).setParameter("id", iMdId).getResultList();
    }

    @Override
    public List<OperationAllowed> findAllWithOwner(int userId, Optional<Specification<OperationAllowed>> specification) {
        CriteriaBuilder cb = _entityManager.getCriteriaBuilder();
        CriteriaQuery<OperationAllowed> query = cb.createQuery(OperationAllowed.class);
        Root<OperationAllowed> operationAllowedRoot = query.from(OperationAllowed.class);
        Root<Metadata> metadataRoot = query.from(Metadata.class);

        query.select(operationAllowedRoot);

        Predicate userEqualsPredicate = cb.equal(metadataRoot.get(Metadata_.sourceInfo).get(MetadataSourceInfo_.owner), userId);
        Predicate mdIdEquals = cb.equal(metadataRoot.get(Metadata_.id), operationAllowedRoot.get(OperationAllowed_.id).get
            (OperationAllowedId_
                .metadataId));
        if (specification.isPresent()) {
            Predicate otherPredicate = specification.get().toPredicate(operationAllowedRoot, query, cb);
            query.where(mdIdEquals, userEqualsPredicate, otherPredicate);
        } else {
            query.where(mdIdEquals, userEqualsPredicate);
        }

        return _entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<Integer> findAllIds(Specification<OperationAllowed> spec, SingularAttribute<OperationAllowedId, Integer> idAttribute) {
        final CriteriaBuilder cb = _entityManager.getCriteriaBuilder();
        final CriteriaQuery<Integer> query = cb.createQuery(Integer.class);
        final Root<OperationAllowed> root = query.from(OperationAllowed.class);
        query.where(spec.toPredicate(root, query, cb));
        query.select(root.get(OperationAllowed_.id).get(idAttribute));
        query.distinct(true);
        return _entityManager.createQuery(query).getResultList();
    }
}
