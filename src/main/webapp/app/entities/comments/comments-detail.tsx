import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './comments.reducer';

export const CommentsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const commentsEntity = useAppSelector(state => state.comments.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="commentsDetailsHeading">Comments</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{commentsEntity.id}</dd>
          <dt>
            <span id="date">Date</span>
          </dt>
          <dd>{commentsEntity.date ? <TextFormat value={commentsEntity.date} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="description">Description</span>
          </dt>
          <dd>{commentsEntity.description}</dd>
          <dt>Plant</dt>
          <dd>{commentsEntity.plant ? commentsEntity.plant.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/comments" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/comments/${commentsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default CommentsDetail;
