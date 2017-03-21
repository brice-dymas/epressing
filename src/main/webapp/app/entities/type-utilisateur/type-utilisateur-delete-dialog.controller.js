(function() {
    'use strict';

    angular
        .module('epressingApp')
        .controller('TypeUtilisateurDeleteController',TypeUtilisateurDeleteController);

    TypeUtilisateurDeleteController.$inject = ['$uibModalInstance', 'entity', 'TypeUtilisateur'];

    function TypeUtilisateurDeleteController($uibModalInstance, entity, TypeUtilisateur) {
        var vm = this;

        vm.typeUtilisateur = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TypeUtilisateur.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
