import { User } from '../../shared';
import { Category } from '../category';
export class Donation_log {
    constructor(
        public id?: number,
        public amount?: number,
        public processed?: boolean,
        public user?: User,
        public category?: Category,
    ) { }
}
