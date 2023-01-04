import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Plant from './plant';
import PlantDetail from './plant-detail';
import PlantUpdate from './plant-update';
import PlantDeleteDialog from './plant-delete-dialog';

const PlantRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Plant />} />
    <Route path="new" element={<PlantUpdate />} />
    <Route path=":id">
      <Route index element={<PlantDetail />} />
      <Route path="edit" element={<PlantUpdate />} />
      <Route path="delete" element={<PlantDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PlantRoutes;
