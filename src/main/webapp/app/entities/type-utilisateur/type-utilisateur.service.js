(function() {
    'use strict';
    angular
        .module('epressingApp')
        .factory('TypeUtilisateur', TypeUtilisateur);

    TypeUtilisateur.$inject = ['$resource'];

    function TypeUtilisateur ($resource) {
        var resourceUrl =  'api/type-utilisateurs/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
