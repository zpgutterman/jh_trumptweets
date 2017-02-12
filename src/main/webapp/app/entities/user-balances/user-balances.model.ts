import { User } from '../../shared';
import { Category } from '../category';
export class User_balances {
    constructor(
        public id?: number,
        public balance?: number,
        public user?: User,
        public category?: Category,
    ) { }
}
