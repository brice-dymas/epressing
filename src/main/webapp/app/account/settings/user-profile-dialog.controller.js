(function() {
    'use strict';

    angular
        .module('epressingApp')
        .controller('UserProfileDialogController', UserProfileDialogController);

    UserProfileDialogController.$inject = ['Principal', 'Auth', 'JhiLanguageService', '$translate', '$scope', '$stateParams', '$uibModalInstance', ];

    function UserProfileDialogController (Principal, Auth, JhiLanguageService, $translate, $scope, $stateParams, $uibModalInstance) {
        var vm = this;

        vm.error = null;
        vm.clear = clear;
        vm.save = save;
        vm.settingsAccount = null;
        vm.success = null;


        function clear () {
            $uibModalInstance.dismiss('cancel');
        }
        function onSaveSuccess (result) {
            $scope.$emit('epressingApp:utilisateurUpdate', result);
            $uibModalInstance.close(result);
        }
        /**
         * Store the "settings account" in a separate variable, and not in the shared "account" variable.
         */
        var copyAccount = function (account) {
            return {
                activated: account.activated,
                email: account.email,
                firstName: account.firstName,
                langKey: account.langKey,
                lastName: account.lastName,
                login: account.login
            };
        };

        Principal.identity().then(function(account) {
            vm.settingsAccount = copyAccount(account);
        });

        function save () {
            Auth.updateAccount(vm.settingsAccount).then(function() {
                vm.error = null;
                vm.success = 'OK';
                Principal.identity(true).then(function(account) {                    
                    vm.settingsAccount = copyAccount(account);
                    $uibModalInstance.dismiss('cancel');
                });
                JhiLanguageService.getCurrent().then(function(current) {
                    if (vm.settingsAccount.langKey !== current) {
                        $translate.use(vm.settingsAccount.langKey);
                    }
                });
                
            }).catch(function() {
                vm.success = null;
                vm.error = 'ERROR';
            });
        }
    }
})();
