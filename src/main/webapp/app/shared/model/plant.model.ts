import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';
import { IComments } from 'app/shared/model/comments.model';

export interface IPlant {
  id?: number;
  name?: string | null;
  location?: string | null;
  datePlant?: string | null;
  waterPerHour?: number | null;
  user?: IUser | null;
  comments?: IComments[] | null;
}

export const defaultValue: Readonly<IPlant> = {};
