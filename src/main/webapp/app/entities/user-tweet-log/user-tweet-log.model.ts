import { User } from '../../shared';
import { Tweetlog } from '../tweetlog';
export class User_tweet_log {
    constructor(
        public id?: number,
        public charge?: number,
        public user?: User,
        public tweet?: Tweetlog,
    ) { }
}
