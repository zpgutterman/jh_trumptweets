import { Category } from '../category';
import { User } from '../../shared';
export class Tweetlog {
    constructor(
        public id?: number,
        public tweet?: string,
        public tweet_date?: any,
        public handle?: string,
        public processed?: boolean,
        public categorize_time?: any,
        public category?: Category,
        public categorize_user?: User,
    ) { }
}
