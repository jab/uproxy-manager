# uproxy-manager

A [Hoplon][1] implementation of the uproxy-manager web UI.

## Dependencies

- Java 1.8
- [boot][2]


## Development

1. Start the `dev` task. In a terminal run:
    ```bash
    $ boot dev
    ```
    This will give you a  Hoplon development setup with:
    - auto compilation on file changes
    - audible warning for compilation success or failures
    - auto reload the html page on changes
    - Clojurescript REPL

1. Go to [http://localhost:3000](http://localhost:3000) in your browser. The UI should load.

1. If you edit and save a file, the task will recompile the code and reload the
   browser to show the updated version.


## Production

1. Run the `prod` task. In a terminal run:
    ```bash
    $ boot prod
    ```

1. The compiled files will be in the `target/` directory. This will use
   advanced compilation and prerender the html.


## License

See [LICENSE](./LICENSE).


[1]: http://hoplon.io
[2]: http://boot-clj.com
