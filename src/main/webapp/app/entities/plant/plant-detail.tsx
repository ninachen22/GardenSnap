import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './plant.reducer';

export const PlantDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const plantEntity = useAppSelector(state => state.plant.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="plantDetailsHeading">Plant</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{plantEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{plantEntity.name}</dd>
          <dt>
            <span id="location">Location</span>
          </dt>
          <dd>{plantEntity.location}</dd>
          <dt>
            <span id="datePlant">Date Plant</span>
          </dt>
          <dd>{plantEntity.datePlant ? <TextFormat value={plantEntity.datePlant} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="waterPerHour">Water Per Hour</span>
          </dt>
          <dd>{plantEntity.waterPerHour}</dd>
          <dt>User</dt>
          <dd>{plantEntity.user ? plantEntity.user.login : ''}</dd>
        </dl>
        <Button tag={Link} to="/plant" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/plant/${plantEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default PlantDetail;
