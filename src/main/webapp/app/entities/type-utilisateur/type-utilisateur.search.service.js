(function() {
    'use strict';

    angular
        .module('epressingApp')
        .factory('TypeUtilisateurSearch', TypeUtilisateurSearch);

    TypeUtilisateurSearch.$inject = ['$resource'];

    function TypeUtilisateurSearch($resource) {
        var resourceUrl =  'api/_search/type-utilisateurs/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
