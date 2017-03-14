import { User } from '../../shared';
import { Category } from '../category';
import { User_tweet_log } from '../user-tweet-log';
export class Donation_log {
    constructor(
        public id?: number,
        public amount?: number,
        public processed?: boolean,
        public processed_date?: any,
        public user?: User,
        public category?: Category,
        public user_tweet_log?: User_tweet_log,
    ) { }
}
