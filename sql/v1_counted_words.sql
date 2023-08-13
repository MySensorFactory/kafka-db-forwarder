CREATE TABLE IF NOT EXISTS factory_data.counted_words
(
    id uuid NOT NULL,
    "timestamp" timestamp with time zone,
    letters_count bigint,
    CONSTRAINT counted_words_pkey PRIMARY KEY (id)
)
    TABLESPACE pg_default;

ALTER TABLE IF EXISTS counted_words
    OWNER to "postgres";