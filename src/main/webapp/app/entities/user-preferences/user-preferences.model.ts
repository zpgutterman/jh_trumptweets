import { User } from '../../shared';
import { Category } from '../category';
export class User_preferences {
    constructor(
        public id?: number,
        public user?: User,
        public excluded_categories?: Category,
    ) { }
}
