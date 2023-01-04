import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPlant } from 'app/shared/model/plant.model';
import { getEntities as getPlants } from 'app/entities/plant/plant.reducer';
import { IComments } from 'app/shared/model/comments.model';
import { getEntity, updateEntity, createEntity, reset } from './comments.reducer';

export const CommentsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const plants = useAppSelector(state => state.plant.entities);
  const commentsEntity = useAppSelector(state => state.comments.entity);
  const loading = useAppSelector(state => state.comments.loading);
  const updating = useAppSelector(state => state.comments.updating);
  const updateSuccess = useAppSelector(state => state.comments.updateSuccess);

  const handleClose = () => {
    navigate('/comments');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getPlants({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...commentsEntity,
      ...values,
      plant: plants.find(it => it.id.toString() === values.plant.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...commentsEntity,
          plant: commentsEntity?.plant?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="gardenSnap3App.comments.home.createOrEditLabel" data-cy="CommentsCreateUpdateHeading">
            Create or edit a Comments
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="comments-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Date" id="comments-date" name="date" data-cy="date" type="date" />
              <ValidatedField label="Description" id="comments-description" name="description" data-cy="description" type="text" />
              <ValidatedField id="comments-plant" name="plant" data-cy="plant" label="Plant" type="select">
                <option value="" key="0" />
                {plants
                  ? plants.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/comments" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default CommentsUpdate;
