(function() {
    'use strict';

    angular
        .module('epressingApp')
        .factory('CommandeSearch', CommandeSearch);

    CommandeSearch.$inject = ['$resource'];

    function CommandeSearch($resource) {
        var resourceUrl =  'api/_search/commandes/:id';
        var commandsOfCurrentUser = 'api/commandes/user/:id'

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'getAllCommandsOfCurrentUser': { url: commandsOfCurrentUser, method: 'GET', isArray: true},
        });
    }
})();
