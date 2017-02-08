import { Category } from '../category';
export class Charity {
    constructor(
        public id?: number,
        public name?: string,
        public website?: string,
        public category?: Category,
    ) { }
}
