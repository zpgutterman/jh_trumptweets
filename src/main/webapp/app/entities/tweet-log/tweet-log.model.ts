export class Tweet_log {
    constructor(
        public id?: number,
        public tweet?: string,
        public tweet_date?: any,
        public handle?: string,
        public processed?: boolean,
        public categorize_time?: any,
    ) { }
}
