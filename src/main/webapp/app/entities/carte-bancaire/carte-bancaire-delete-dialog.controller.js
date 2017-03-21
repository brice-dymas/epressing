(function() {
    'use strict';

    angular
        .module('epressingApp')
        .controller('CarteBancaireDeleteController',CarteBancaireDeleteController);

    CarteBancaireDeleteController.$inject = ['$uibModalInstance', 'entity', 'CarteBancaire'];

    function CarteBancaireDeleteController($uibModalInstance, entity, CarteBancaire) {
        var vm = this;

        vm.carteBancaire = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CarteBancaire.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
