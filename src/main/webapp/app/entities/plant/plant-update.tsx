import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { IPlant } from 'app/shared/model/plant.model';
import { getEntity, updateEntity, createEntity, reset } from './plant.reducer';

export const PlantUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const users = useAppSelector(state => state.userManagement.users);
  const plantEntity = useAppSelector(state => state.plant.entity);
  const loading = useAppSelector(state => state.plant.loading);
  const updating = useAppSelector(state => state.plant.updating);
  const updateSuccess = useAppSelector(state => state.plant.updateSuccess);

  const handleClose = () => {
    navigate('/plant');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getUsers({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...plantEntity,
      ...values,
      user: users.find(it => it.id.toString() === values.user.toString()),
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
          ...plantEntity,
          user: plantEntity?.user?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="gardenSnap3App.plant.home.createOrEditLabel" data-cy="PlantCreateUpdateHeading">
            Create or edit a Plant
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="plant-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Name" id="plant-name" name="name" data-cy="name" type="text" />
              <ValidatedField label="Location" id="plant-location" name="location" data-cy="location" type="text" />
              <ValidatedField label="Date Plant" id="plant-datePlant" name="datePlant" data-cy="datePlant" type="date" />
              <ValidatedField label="Water Per Hour" id="plant-waterPerHour" name="waterPerHour" data-cy="waterPerHour" type="text" />
              <ValidatedField id="plant-user" name="user" data-cy="user" label="User" type="select">
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.login}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/plant" replace color="info">
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

export default PlantUpdate;
