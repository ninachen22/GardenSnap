import dayjs from 'dayjs';
import { IPlant } from 'app/shared/model/plant.model';

export interface IComments {
  id?: number;
  date?: string | null;
  description?: string | null;
  plant?: IPlant | null;
}

export const defaultValue: Readonly<IComments> = {};
