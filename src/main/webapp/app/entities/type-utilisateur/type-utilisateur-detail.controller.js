(function() {
    'use strict';

    angular
        .module('epressingApp')
        .controller('TypeUtilisateurDetailController', TypeUtilisateurDetailController);

    TypeUtilisateurDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TypeUtilisateur'];

    function TypeUtilisateurDetailController($scope, $rootScope, $stateParams, previousState, entity, TypeUtilisateur) {
        var vm = this;

        vm.typeUtilisateur = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('epressingApp:typeUtilisateurUpdate', function(event, result) {
            vm.typeUtilisateur = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
