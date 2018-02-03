(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('NurseDialogController', NurseDialogController);

    NurseDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Nurse', 'UserBD', 'Company', 'Gender'];

    function NurseDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Nurse, UserBD, Company, Gender) {
        var vm = this;

        vm.nurse = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.userbds = UserBD.query({filter: 'nurse-is-null'});
        $q.all([vm.nurse.$promise, vm.userbds.$promise]).then(function() {
            if (!vm.nurse.userBD || !vm.nurse.userBD.id) {
                return $q.reject();
            }
            return UserBD.get({id : vm.nurse.userBD.id}).$promise;
        }).then(function(userBD) {
            vm.userbds.push(userBD);
        });
        vm.companies = Company.query();
        vm.genders = Gender.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.nurse.id !== null) {
                Nurse.update(vm.nurse, onSaveSuccess, onSaveError);
            } else {
                Nurse.save(vm.nurse, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cercardiobitiApp:nurseUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.createdat = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
