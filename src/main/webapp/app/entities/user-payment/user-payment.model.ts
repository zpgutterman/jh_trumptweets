import { User } from '../../shared';
export class User_payment {
    constructor(
        public id?: number,
        public token?: string,
        public name?: string,
        public method?: string,
        public user?: User,
    ) { }
}
