import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Comments from './comments';
import CommentsDetail from './comments-detail';
import CommentsUpdate from './comments-update';
import CommentsDeleteDialog from './comments-delete-dialog';

const CommentsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Comments />} />
    <Route path="new" element={<CommentsUpdate />} />
    <Route path=":id">
      <Route index element={<CommentsDetail />} />
      <Route path="edit" element={<CommentsUpdate />} />
      <Route path="delete" element={<CommentsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CommentsRoutes;
