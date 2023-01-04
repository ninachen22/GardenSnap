import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPlant } from 'app/shared/model/plant.model';
import { getEntities } from './plant.reducer';

export const Plant = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const plantList = useAppSelector(state => state.plant.entities);
  const loading = useAppSelector(state => state.plant.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="plant-heading" data-cy="PlantHeading">
        Plants
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/plant/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Plant
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {plantList && plantList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Location</th>
                <th>Date Plant</th>
                <th>Water Per Hour</th>
                <th>User</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {plantList.map((plant, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/plant/${plant.id}`} color="link" size="sm">
                      {plant.id}
                    </Button>
                  </td>
                  <td>{plant.name}</td>
                  <td>{plant.location}</td>
                  <td>{plant.datePlant ? <TextFormat type="date" value={plant.datePlant} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>{plant.waterPerHour}</td>
                  <td>{plant.user ? plant.user.login : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/plant/${plant.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/plant/${plant.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`/plant/${plant.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Plants found</div>
        )}
      </div>
    </div>
  );
};

export default Plant;
